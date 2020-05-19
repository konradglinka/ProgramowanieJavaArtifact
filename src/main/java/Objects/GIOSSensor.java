package Objects;

public class GIOSSensor {
    private int IDStation;
    private int IDSensor;
    private String nameOfSensor;
    private String shortNameOfSensor;

    public GIOSSensor(int IDStation, int IDSensor, String nameOfSensor, String shortNameOfSensor) {
        this.IDStation = IDStation;
        this.IDSensor = IDSensor;
        this.nameOfSensor = nameOfSensor;
        this.shortNameOfSensor = shortNameOfSensor;
    }

    public int getIDStation() {
        return IDStation;
    }

    public void setIDStation(int IDStation) {
        this.IDStation = IDStation;
    }

    public int getIDSensor() {
        return IDSensor;
    }

    public void setIDSensor(int IDSensor) {
        this.IDSensor = IDSensor;
    }

    public String getNameOfSensor() {
        return nameOfSensor;
    }

    public void setNameOfSensor(String nameOfSensor) {
        this.nameOfSensor = nameOfSensor;
    }

    public String getShortNameOfSensor() {
        return shortNameOfSensor;
    }

    public void setShortNameOfSensor(String shortNameOfSensor) {
        this.shortNameOfSensor = shortNameOfSensor;
    }
}

