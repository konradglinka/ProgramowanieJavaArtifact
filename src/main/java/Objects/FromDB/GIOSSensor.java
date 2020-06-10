package Objects.FromDB;

public class GIOSSensor {
    private final int IDStation;
    private final int IDSensor;
    private final String nameOfSensor;
    private final String shortNameOfSensor;

    public GIOSSensor(int IDStation, int IDSensor, String nameOfSensor, String shortNameOfSensor) {
        this.IDStation = IDStation;
        this.IDSensor = IDSensor;
        this.nameOfSensor = nameOfSensor;
        this.shortNameOfSensor = shortNameOfSensor;
    }

    public int getIDStation() {
        return IDStation;
    }

    public String getNameOfSensor() {
        return nameOfSensor;
    }

    public int getIDSensor() {
        return IDSensor;
    }



    public String getShortNameOfSensor() {
        return shortNameOfSensor;
    }


}

