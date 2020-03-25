package MeasuresFromUsers;



import java.util.ArrayList;

public class MeasuresPLNamesFactory {
   private  ArrayList<String> namesOfMeasuresArraylist=new ArrayList<>();

    public MeasuresPLNamesFactory() {
        namesOfMeasuresArraylist.add("Temperatura powietrza");
        namesOfMeasuresArraylist.add("Ciśnienie");
   namesOfMeasuresArraylist.add("Prędkość wiatru");
        namesOfMeasuresArraylist.add("Wilgotność powietrza");
        namesOfMeasuresArraylist.add("Zachmurzenie");
    }

    public ArrayList<String> getNamesOfMeasuresArraylist() {
        return namesOfMeasuresArraylist;
    }
}
