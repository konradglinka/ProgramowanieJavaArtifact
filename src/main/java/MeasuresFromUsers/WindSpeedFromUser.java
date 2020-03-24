package MeasuresFromUsers;

public class WindSpeedFromUser {
    private String date=null;
    private String userName=null;

    private double windSpeed=0.0;

    public WindSpeedFromUser(String date, String userName, double windSpeed) {
        this.date = date;
        this.userName = userName;
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

}
