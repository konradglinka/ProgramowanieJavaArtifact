package Objects.FromDB;

public class TemperatureFromUser { //Klasa reprezentuje pomiar temperatury powietrza od użytkownika
    private String date=null;
    private String userName=null;
    private int id =0;
    private double temperature=0.0;




    public TemperatureFromUser(String date, String userName, double temperature, int id) {
        this.date = date;
        this.userName = userName;
        this.temperature = temperature;
        this.id = id;
    }

    public int getId() {
        return id;
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
