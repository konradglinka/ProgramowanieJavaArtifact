package MeasuresFromUsers;

public class MesureFromUser {
    String temperature;
    String windSpeed;
    String humidity;
    String claudiness;
    String pressure;
    String city;
    String userName;
    String date;


    public MesureFromUser(String temperature, String windSpeed, String humidity, String claudiness, String pressure, String city, String userName, String date) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.claudiness = claudiness;
        this.pressure = pressure;
        this.city = city;
        this.userName = userName;
        this.date = date;
    }
}
