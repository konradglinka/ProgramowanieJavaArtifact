package MeasuresFromUsers;

import java.util.ArrayList;

public class TablesForCityFactory {

    public ArrayList<TemperaturesFromUser> showTemperaturesFromUserInCity(String city,ArrayList<TemperaturesFromUser> temperaturesFromUserArrayList){
 ArrayList<TemperaturesFromUser> result=new ArrayList<>();
        for(int i=0; i<temperaturesFromUserArrayList.size();i++){
            if(temperaturesFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new TemperaturesFromUser(temperaturesFromUserArrayList.get(i).getDate(), temperaturesFromUserArrayList.get(i).getUserName(), temperaturesFromUserArrayList.get(i).getTemperature(), temperaturesFromUserArrayList.get(i).getCity()));
            }
            }
 return result;
    }
}
