package pl.spc.assailant.akka.configuration;

public class AkkaAssilantConfiguration {
    private String host;
    private int port;
    private DataSourceConfiguration dbConfiguration;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public DataSourceConfiguration getDbConfiguration() {
        return dbConfiguration;
    }

    public void setDbConfiguration(DataSourceConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    public static final class DataSourceConfiguration {
        private String url;
        private String user;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
