package MeasuresFromUsers;

import MeasuresFromUsers.TypeOfMeasure.*;

import java.util.ArrayList;

public class TablesForCityFactory { //Klasa zawiera metody zwracjące lity konkretnych pomiarów dla wybranego miasta
    // (Wyciaga z listy ze wszystkimi miastami pomiary tylko dla danego miasta)

    public ArrayList<TemperatureFromUser> showTemperaturesFromUserInCity(String city, ArrayList<TemperatureFromUser> temperaturesFromUserArrayList) {
        ArrayList<TemperatureFromUser> result = new ArrayList<>();
        for (int i = 0; i < temperaturesFromUserArrayList.size(); i++) {
            if (temperaturesFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new TemperatureFromUser(temperaturesFromUserArrayList.get(i).getDate(), temperaturesFromUserArrayList.get(i).getUserName(), temperaturesFromUserArrayList.get(i).getTemperature(), temperaturesFromUserArrayList.get(i).getCity()));
            }
        }
        return result;
    }

    public ArrayList<PressureFromUser> showPressureFromUserInCity(String city, ArrayList<PressureFromUser> pressureFromUserArrayList) {
        ArrayList<PressureFromUser> result = new ArrayList<>();
        for (int i = 0; i < pressureFromUserArrayList.size(); i++) {
            if (pressureFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new PressureFromUser(pressureFromUserArrayList.get(i).getDate(), pressureFromUserArrayList.get(i).getUserName(), pressureFromUserArrayList.get(i).getPressure(), pressureFromUserArrayList.get(i).getCity()));
            }
        }
        return result;
    }

    public ArrayList<WindSpeedFromUser> showWindSpeedFromUserInCity(String city, ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList) {
        ArrayList<WindSpeedFromUser> result = new ArrayList<>();
        for (int i = 0; i < windSpeedFromUserArrayList.size(); i++) {
            if (windSpeedFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new WindSpeedFromUser(windSpeedFromUserArrayList.get(i).getDate(), windSpeedFromUserArrayList.get(i).getUserName(), windSpeedFromUserArrayList.get(i).getWindSpeed(), windSpeedFromUserArrayList.get(i).getCity()));
            }
        }
        return result;
    }

    public ArrayList<HumidityFromUser> showHumidityFromUserInCity(String city, ArrayList<HumidityFromUser> humidityFromUserArrayList) {
        ArrayList<HumidityFromUser> result = new ArrayList<>();
        for (int i = 0; i < humidityFromUserArrayList.size(); i++) {
            if (humidityFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new HumidityFromUser(humidityFromUserArrayList.get(i).getDate(), humidityFromUserArrayList.get(i).getUserName(), humidityFromUserArrayList.get(i).getHumidity(), humidityFromUserArrayList.get(i).getCity()));
            }
        }
        return result;
    }

    public ArrayList<ClaudinessFromUser> showClaudinessFromUserInCity(String city, ArrayList<ClaudinessFromUser> claudinessFromUserArrayList) {
        ArrayList<ClaudinessFromUser> result = new ArrayList<>();
        for (int i = 0; i < claudinessFromUserArrayList.size(); i++) {
            if (claudinessFromUserArrayList.get(i).getCity().equals(city)) {
                result.add(new ClaudinessFromUser(claudinessFromUserArrayList.get(i).getDate(), claudinessFromUserArrayList.get(i).getUserName(), claudinessFromUserArrayList.get(i).getClaudiness(), claudinessFromUserArrayList.get(i).getCity()));
            }
        }
        return result;
    }


}
