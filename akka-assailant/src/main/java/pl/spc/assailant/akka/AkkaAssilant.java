package pl.spc.assailant.akka;

import java.util.concurrent.TimeUnit;

import com.google.inject.Guice;
import com.google.inject.Injector;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import pl.spc.assailant.akka.configuration.AkkaAssilantConfiguration;
import pl.spc.assailant.akka.configuration.AkkaAssilantConfigurer;
import pl.spc.assailant.akka.http.HttpEndpoints;

public class AkkaAssilant {
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            throw new IllegalStateException("No arguments provided");
        }

        Injector injector = Guice.createInjector(new AkkaAssilantConfigurer(args[0]));
        HttpEndpoints httpEndpoints = injector.getInstance(HttpEndpoints.class);
        AkkaAssilantConfiguration configuration = injector.getInstance(AkkaAssilantConfiguration.class);

        ActorSystem actorSystem = ActorSystem.create();
        ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        Flow<HttpRequest, HttpResponse, NotUsed> flow = httpEndpoints.createRoute().flow(actorSystem, materializer);
        Http.get(actorSystem).bindAndHandle(flow, ConnectHttp.toHost(configuration.getHost(), configuration.getPort()), materializer);

        while (true) {
            Thread.sleep(TimeUnit.MINUTES.toMillis(3));
        }
    }
}
