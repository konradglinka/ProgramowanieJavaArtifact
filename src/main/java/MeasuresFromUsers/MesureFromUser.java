package MeasuresFromUsers;

public class MesureFromUser { //Klasa reprezentuje 1 dodany pomiar przez użytkownika

    //POMIARY SĄ WCZYTWANE JAKO OBIEKTY TEJ KLASY A DOPIERO POTEM DZIELONE NA KONKRETNE RODZAJE POMIARÓW NP.PressureFromUser

    private int id = 0;
    private String date = null;
    private String userName = null;
    private double temperature = 0.0;
    private double windSpeed = 0.0;
    private double humidity = 0.0;
    private String claudiness = null;
    private double pressure = 0.0;
    private String city = null;


    public MesureFromUser(int id, String date, String userName, double temperature,
                          double windSpeed, double humidity, String claudiness, double pressure, String city) {
        this.id = id;
        this.date = date;
        this.userName = userName;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.claudiness = claudiness;
        this.pressure = pressure;
        this.city = city;
    }


    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getClaudiness() {
        return claudiness;
    }

    public double getPressure() {
        return pressure;
    }

    public String getCity() {
        return city;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }


}