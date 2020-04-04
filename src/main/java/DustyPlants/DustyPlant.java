package DustyPlants;

public class DustyPlant { //Klasa reprezentuje dowolną rośline pylącą

    private String name; //nazwa rośliny
    private int startDustMonth; //Początek pylenia rośliny
    private int endDustMonth; //Koniec pylenia rośliny

    //NIE ISTNIEJĄ ROŚLINY KTÓRE PYLĄ W KILKU OKRESACH CZASU
    // .KAŻDA ROSLINA MA OKRES OD PEWNEGO MIESIACA DO INNEGO MIESIĄCA


    public DustyPlant(String name, int startDustMonth, int endDustMonth) {
        this.name = name;
        this.startDustMonth = startDustMonth;
        this.endDustMonth = endDustMonth;
    }

    public String getName() {
        return name;
    }

    public int getStartDustMonth() {
        return startDustMonth;
    }

    public int getEndDustMonth() {
        return endDustMonth;
    }


}
