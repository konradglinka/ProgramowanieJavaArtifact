package MeasuresFromUsers.TypeOfMeasure;

public class TemperatureFromUser { //Klasa reprezentuje pomiar temperatury powietrza od użytkownika
    private String date=null;
    private String userName=null;
    private String city=null;
    private double temperature=0.0;




    public TemperatureFromUser(String date, String userName, double temperature, String city) {
        this.date = date;
        this.userName = userName;
        this.temperature = temperature;
        this.city = city;
    }
    public String getCity() {
        return city;
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
