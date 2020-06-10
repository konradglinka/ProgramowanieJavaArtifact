package Objects.FromDB;

public class DustyPlant {

    private String name;
    private int startDustMonth;
    private int endDustMonth;
    private int startDustDay;
    private int endDustDay;

    public DustyPlant(String name, int startDustMonth, int endDustMonth, int startDustDay, int endDustDay) {
        this.name = name;
        this.startDustMonth = startDustMonth;
        this.endDustMonth = endDustMonth;
        this.startDustDay = startDustDay;
        this.endDustDay = endDustDay;
    }

    public String getName() {
        return name;
    }

    public int getStartDustMonth() {
        return startDustMonth;
    }

    public int getStartDustDay(){return startDustDay;}

    public int getEndDustMonth() {
        return endDustMonth;
    }

    public int getEndDustDay() {return endDustDay;}

}
