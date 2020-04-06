package DustyPlants;

import java.time.LocalDate;
import java.util.ArrayList;

public class ActualDustyPlantsFactory { //klasa zawiera metode dostaje wszystkie rośliny pylące,
    // a zwraca liste nazw tych które pylą aktulanie


    public ArrayList<String> listOfActualDustyPlants() {

        LocalDate actualDate = LocalDate.now();
        ArrayList<String> listOfActualDustyPlantsNames = new ArrayList<>(); //Lista aktualnie pylących roślin
        DustyPlantsFactory dustyPlantsFactory = new DustyPlantsFactory();
        int actualmonth = actualDate.getMonthValue(); //Aktualny miesiąc
        //WSZYSTKIE ROŚLINY WRAZ Z OKRESAMI ICH PYLENIA SĄ W DOSTĘPNE POPRZEZ DustyPlantsFactory
        for (int i = 0; i < dustyPlantsFactory.getDustyPlantArraylist().size(); i++) {
            if (actualmonth >= dustyPlantsFactory.getDustyPlantArraylist().get(i).getStartDustMonth() &&
                    actualmonth <= dustyPlantsFactory.getDustyPlantArraylist().get(i).getEndDustMonth()) {
                listOfActualDustyPlantsNames.add(dustyPlantsFactory.getDustyPlantArraylist().get(i).getName());
            }
        }
        return listOfActualDustyPlantsNames;
    }
}

