package Objects.FromDB;

public class WindSpeedFromUser { //Klasa reprezentuje pomiar prędkości wiatru  od użytkownika
    private String date=null;
    private String userName=null;

    private double windSpeed=0.0;
    private int id=0;

    public WindSpeedFromUser(String date, String userName, double windSpeed, int id) {
        this.date = date;
        this.userName = userName;
        this.windSpeed = windSpeed;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getID() {
        return id;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
