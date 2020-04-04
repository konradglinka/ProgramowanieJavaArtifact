package MeasuresFromUsers.TypeOfMeasure;

public class PressureFromUser { //Klasa reprezentuje pomiar ciśnienia powietrza od użytkownika
    private String date=null;
    private String userName=null;

    private double pressure=0.0;
    private String city=null;

    public PressureFromUser(String date, String userName, double pressure, String city) {
        this.date = date;
        this.userName = userName;
        this.pressure = pressure;
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public double getPressure() {
        return pressure;
    }

    public String getCity() {
        return city;
    }
}
