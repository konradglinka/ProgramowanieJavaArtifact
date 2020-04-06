package MeasuresFromUsers.TypeOfMeasure;

public class WindSpeedFromUser { //Klasa reprezentuje pomiar prędkości wiatru  od użytkownika
    private String date=null;
    private String userName=null;

    private double windSpeed=0.0;
    private String city=null;

    public WindSpeedFromUser(String date, String userName, double windSpeed, String city) {
        this.date = date;
        this.userName = userName;
        this.windSpeed = windSpeed;
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
