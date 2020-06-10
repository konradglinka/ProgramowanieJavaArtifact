package Objects.FromDB;

public class HumidityFromUser { //Klasa reprezentuje pomiar wilgotności od użytkownika
    private String date=null;
    private String userName=null;

    private double humidity=0.0;
    private int id=0;
    public HumidityFromUser(String date, String userName, double humidity, int id) {
        this.date = date;
        this.userName = userName;
        this.humidity = humidity;
      this.id=id;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public double getHumidity() {
        return humidity;
    }
}
