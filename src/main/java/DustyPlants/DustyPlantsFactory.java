package DustyPlants;

import DustyPlants.DustyPlant;

import java.util.ArrayList;

public class DustyPlantsFactory { //Klasa zawiera wszystkie rośliny pylące i umożliwia dostęp do nich
    ArrayList<DustyPlant>dustyPlantArraylist=new ArrayList<>();


    public DustyPlantsFactory() {
        dustyPlantArraylist.add(new DustyPlant("Trawy",4,9));
        dustyPlantArraylist.add(new DustyPlant("Brzoza",3,5));
        dustyPlantArraylist.add(new DustyPlant("Bylica",7,9));
        dustyPlantArraylist.add(new DustyPlant("Leszczyna",1,4));
        dustyPlantArraylist.add(new DustyPlant("Olsza",2,4));
        dustyPlantArraylist.add(new DustyPlant("Topola",3,4));
        dustyPlantArraylist.add(new DustyPlant("Jesion",4,4));
        dustyPlantArraylist.add(new DustyPlant("Dąb",4,5));
        dustyPlantArraylist.add(new DustyPlant("Babka",5,9));
        dustyPlantArraylist.add(new DustyPlant("Szczaw",5,8));
        dustyPlantArraylist.add(new DustyPlant("Pokrzywa",5,10));
        dustyPlantArraylist.add(new DustyPlant("Komosa",6,9));
        dustyPlantArraylist.add(new DustyPlant("Cladosporium",2,11));
        dustyPlantArraylist.add(new DustyPlant("Alternaria",3,11));
    }

    public ArrayList<DustyPlant> getDustyPlantArraylist() {
        return dustyPlantArraylist;
    }
}
