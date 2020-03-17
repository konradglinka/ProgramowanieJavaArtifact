package DustyPlants;

import DustyPlants.DustyPlantsFactory;

import java.util.ArrayList;

public class ActualDustyPlants {

    public ArrayList<String>listOfActualDustyPlants(){

        ArrayList<String> listOfActualDustyPlantsNames=new ArrayList<>();
        DustyPlantsFactory dustyPlantsFactory= new DustyPlantsFactory();
        for(int i=0;i<dustyPlantsFactory.getDustyPlantArraylist().size();i++){
           // dustyPlantsFactory.getDustyPlantArraylist().get(i).getEndDustMonth();
        }
        listOfActualDustyPlantsNames.add(dustyPlantsFactory.getDustyPlantArraylist().get(1).getName());
        return listOfActualDustyPlantsNames;
    }
}
