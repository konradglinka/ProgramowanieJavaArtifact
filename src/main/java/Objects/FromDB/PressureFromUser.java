package Objects.FromDB;

public class PressureFromUser { //Klasa reprezentuje pomiar ciśnienia powietrza od użytkownika
    private String date=null;
    private String userName=null;

    private double pressure=0.0;
    private int id=0;

    public PressureFromUser(String date, String userName, double pressure, int id) {
        this.date = date;
        this.userName = userName;
        this.pressure = pressure;
        this.id = id;
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

    public int getId() {
        return id;
    }
}
