package MeasuresFromUsers;

public class TemperaturesFromUser {
    private String date=null;
    private String userName=null;
    private double temperature=0.0;
    private String city=null;

    public String getCity() {
        return city;
    }

    public TemperaturesFromUser(String date, String userName, double temperature, String city) {
        this.date = date;
        this.userName = userName;
        this.temperature = temperature;
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public double getTemperature() {
        return temperature;
    }
}
