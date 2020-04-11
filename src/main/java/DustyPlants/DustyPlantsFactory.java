package DustyPlants;

import DustyPlants.DustyPlant;

import java.util.ArrayList;

public class DustyPlantsFactory {
    ArrayList<DustyPlant>dustyPlantArraylist=new ArrayList<>();


    public DustyPlantsFactory() {
        dustyPlantArraylist.add(new DustyPlant("Trawy",4,9,21, 30));
        dustyPlantArraylist.add(new DustyPlant("Brzoza",3,5,21,9));
        dustyPlantArraylist.add(new DustyPlant("Bylica",7,9,1,30));
        dustyPlantArraylist.add(new DustyPlant("Leszczyna",1,4,21,10));
        dustyPlantArraylist.add(new DustyPlant("Olsza",2,4,1,10));
        dustyPlantArraylist.add(new DustyPlant("Topola",3,4,11,30));
        dustyPlantArraylist.add(new DustyPlant("Jesion",4,4,1,30));
        dustyPlantArraylist.add(new DustyPlant("Dąb",4,5,1,31));
        dustyPlantArraylist.add(new DustyPlant("Babka",5,9,1,30));
        dustyPlantArraylist.add(new DustyPlant("Szczaw",5,8,1,31));
        dustyPlantArraylist.add(new DustyPlant("Pokrzywa",5,10,11,10));
        dustyPlantArraylist.add(new DustyPlant("Komosa",6,9,11,20));
        dustyPlantArraylist.add(new DustyPlant("Cladosporium",2,11,11,30));
        dustyPlantArraylist.add(new DustyPlant("Alternaria",3,11,1,10));
    }

    public ArrayList<DustyPlant> getDustyPlantArraylist() {
        return dustyPlantArraylist;
    }
}