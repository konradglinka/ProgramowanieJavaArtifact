package DustyPlants;

import DustyPlants.DustyPlantsFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class ActualDustyPlants {

    public ArrayList<String>listOfActualDustyPlants(){

            LocalDate actualDate=LocalDate.now();
            ArrayList<String> listOfActualDustyPlantsNames=new ArrayList<>();
            DustyPlantsFactory dustyPlantsFactory= new DustyPlantsFactory();
            int actualmonth= actualDate.getMonthValue();
            for(int i=0;i<dustyPlantsFactory.getDustyPlantArraylist().size();i++){
                if(actualmonth>=dustyPlantsFactory.getDustyPlantArraylist().get(i).getStartDustMonth()&&
                        actualmonth<=dustyPlantsFactory.getDustyPlantArraylist().get(i).getEndDustMonth())
                {
                    listOfActualDustyPlantsNames.add(dustyPlantsFactory.getDustyPlantArraylist().get(i).getName());
                }
            }
            return listOfActualDustyPlantsNames;
        }
    }

