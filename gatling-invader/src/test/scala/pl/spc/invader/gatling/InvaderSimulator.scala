package pl.spc.invader.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.util.Random
import scala.concurrent.duration.DurationInt

class InvaderSimulator extends Simulation {
    val httpConf = http
        .baseURL("http://localhost:8080")
        .acceptHeader("application/json")
        .headers(Map("Content-Type" -> "application/json"))

    def employeeJson(name: String, surname: String, salary: String, email: String) =
        s"""{
            "name": "$name",
            "surname": "$surname",
            "email": "$email",
            "salary": {
                "amount": "$salary"
            }
        }"""
    val RND = new Random
    def randomString(length: Int): String = RND.alphanumeric take (length) mkString
    def randomInt: String = RND.nextInt(100000 - 100) + 100 toString
    def randomName = randomString(34)
    def randomEmail = randomString(34) + "@mail.com"
    val employeeFeeder = Iterator.continually(Map("name" -> randomName, "surname" -> randomName, "email" -> randomEmail, "salary" -> randomInt))

    object Create {
        def employee(body: String) = exec(http("create")
            .post("/employees")
            .body(StringBody(body))
            .check(status.is(201))
            .check(regex(""""id":"([.A-Za-z0-9_-]+)"""") saveAs ("empId")))
    }
    object Get {
        val employee = exec(http("get")
            .get("/employees/${empId}")
            .check(status.is(200)))
        val employees = exec(http("getAll")
            .get("/employees")
            .check(status.is(200)))
    }

    val scn = scenario("InvaderSimulator")
        .repeat(100) {
            feed(employeeFeeder)
                .exec(Create.employee(employeeJson("${name}", "${surname}", "${salary}", "${email}")))
                .exec(Get.employee)
        }
        .exec(Get.employees)

    setUp(
        scn.inject(rampUsersPerSec(1).to(15).during(20 seconds))
    ).protocols(httpConf)
}
