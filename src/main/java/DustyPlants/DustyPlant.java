package DustyPlants;

public class DustyPlant {

    private String name;
    private int startDustMonth;
    private int endDustMonth;
    private int stardDustDay;
    private int endDustyDay;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DustyPlant(String name, int startDustMonth, int endDustMonth,int stardDustDay, int endDustyDay) {
        this.name = name;
        this.startDustMonth = startDustMonth;
        this.endDustMonth = endDustMonth;
        this.stardDustDay= stardDustDay;
        this.endDustyDay= endDustyDay;

    }

    public int getStartDustMonth() {
        return startDustMonth;
    }

    public int getStardDustDay(){return stardDustDay;}

    public int getEndDustMonth() {
        return endDustMonth;
    }

    public int getEndDustyDay() {return endDustyDay;}

}
