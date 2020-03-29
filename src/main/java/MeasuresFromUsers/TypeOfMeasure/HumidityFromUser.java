package MeasuresFromUsers.TypeOfMeasure;

public class HumidityFromUser { //Klasa reprezentuje pomiar wilgotności od użytkownika
    private String date=null;
    private String userName=null;

    private double humidity=0.0;
    private String city=null;

    public HumidityFromUser(String date, String userName, double humidity, String city) {
        this.date = date;
        this.userName = userName;
        this.humidity = humidity;
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

    public double getHumidity() {
        return humidity;
    }
}
