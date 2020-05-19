package Repositories.FromDB;

public class UserSettingsRepository {
    private double maxTemperature;
    private double minTemperature;
    private double maxWindSpeed;
    private double minWindSpeed;
    private double minPressure;
    private double maxPressure;
    private double minHumidity=0.0; //Wilgotonosć jest niemodyfikowalna ponieważ jest to wartość procentowa
    private double maxHumidity=100.00;
    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxWindSpeed(double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public void setMinWindSpeed(double minWindSpeed) {
        this.minWindSpeed = minWindSpeed;
    }

    public void setMinPressure(double minPressure) {
        this.minPressure = minPressure;
    }

    public void setMaxPressure(double maxPressure) {
        this.maxPressure = maxPressure;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public double getMinWindSpeed() {
        return minWindSpeed;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public double getMinPressure() {
        return minPressure;
    }

    public double getMaxPressure() {
        return maxPressure;
    }
}
