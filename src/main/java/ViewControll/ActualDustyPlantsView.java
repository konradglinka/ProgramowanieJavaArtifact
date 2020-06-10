package ViewControll;


import Objects.FromDB.DustyPlant;
import Repositories.FromDB.DustyPlantsRepository;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ActualDustyPlantsView {
    public ActualDustyPlantsView(ListView <String>listViewWithActualDustyPlants, DustyPlantsRepository dustyPlantsRepository) {
        ArrayList<DustyPlant>dustyPlants= dustyPlantsRepository.getDustyPlantArraylist();
        LocalDate actualDate=LocalDate.now();//Actual date
        int actualmonth= actualDate.getMonthValue(); //Actual month
        int actualday=actualDate.getDayOfMonth(); //Actual day
        ArrayList<String> listOfActualDustyPlantsNames=new ArrayList<>();
        for(int i = 0; i< dustyPlantsRepository.getDustyPlantArraylist().size(); i++){
            if(actualmonth>=dustyPlants.get(i).getStartDustMonth()&&
                    actualmonth<=dustyPlants.get(i).getEndDustMonth())
            {
                if(actualmonth==dustyPlants.get(i).getStartDustMonth()&&
                        actualday<dustyPlants.get(i).getStartDustDay()
                        ||actualday>dustyPlants.get(i).getEndDustDay()&&
                        actualmonth==dustyPlants.get(i).getEndDustMonth()
                )
                    continue;
                listOfActualDustyPlantsNames.add(dustyPlants.get(i).getName());
            }
        }
        listViewWithActualDustyPlants.getItems().addAll(listOfActualDustyPlantsNames);
    }
}