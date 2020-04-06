package OWM;

import java.util.Date;

public class WeatherMeasureOWM {
    double temp;
    double wind;
    double humidity;
    double pressure;
    String claudiness;
    Date dateOfMeasure;

    public WeatherMeasureOWM(double temp, double wind, double humidity, double pressure, String claudiness, Date dateOfMeasure) {
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.pressure = pressure;
        this.claudiness = claudiness;
        this.dateOfMeasure = dateOfMeasure;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public String getClaudiness() {
        return claudiness;
    }

    public void setClaudiness(String claudiness) {
        this.claudiness = claudiness;
    }

    public Date getDateOfMeasure() {
        return dateOfMeasure;
    }

    public void setDateOfMeasure(Date dateOfMeasure) {
        this.dateOfMeasure = dateOfMeasure;
    }
}

