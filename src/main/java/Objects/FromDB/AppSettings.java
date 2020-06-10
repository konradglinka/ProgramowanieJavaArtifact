package Objects.FromDB;

public class AppSettings {
    private final double maxTemperature;
    private final double minTemperature;
    private final double maxWindSpeed;
    private final double minWindSpeed;
    private final double minPressure;
    private final double maxPressure;
    private final double minHumidity=0.0; //Wilgotonosć jest niemodyfikowalna ponieważ jest to wartość procentowa
    private final double maxHumidity=100.00;

    public AppSettings(double maxTemperature, double minTemperature, double maxWindSpeed, double minWindSpeed, double minPressure, double maxPressure) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.maxWindSpeed = maxWindSpeed;
        this.minWindSpeed = minWindSpeed;
        this.minPressure = minPressure;
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

    public double getMinPressure() {
        return minPressure;
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }
}
