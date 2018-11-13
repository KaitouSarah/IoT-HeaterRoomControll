package no.hvl.dat159.roomcontrol;

public class Logindetails {
    private String broker = "tcp://m15.cloudmqtt.com:15385";
    private String username = "kxrqqumi";
    private String password = "YHvh_Ti7bYBF";

    Logindetails () {

    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
