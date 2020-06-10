package Repositories.FromDB;

import Objects.FromDB.*;

import java.util.ArrayList;

public class UserMeasuresRepository { //Klasa zawiera metody zwracające listy konkretnych pomiarów dla wybranego miasta
    // (Wyciaga z listy ze wszystkimi miastami pomiary tylko dla danego miasta)

    public ArrayList<TemperatureFromUser> showTemperaturesFromUsersInCity(int ID, ArrayList<TemperatureFromUser>
            temperaturesFromUserArrayList) {
        ArrayList<TemperatureFromUser> result = new ArrayList<>();
        for (int i = 0; i < temperaturesFromUserArrayList.size(); i++) {
            if (temperaturesFromUserArrayList.get(i).getId()==ID) {
                result.add(new TemperatureFromUser(temperaturesFromUserArrayList.get(i).getDate(),
                        temperaturesFromUserArrayList.get(i).getUserName(), temperaturesFromUserArrayList.get(i).getTemperature(),
                        temperaturesFromUserArrayList.get(i).getId()));
            }
        }
        return result;
    }

    public ArrayList<PressureFromUser> showPressureFromUsersInCity(int ID, ArrayList<PressureFromUser> pressureFromUserArrayList) {
        ArrayList<PressureFromUser> result = new ArrayList<>();
        for (int i = 0; i < pressureFromUserArrayList.size(); i++) {
            if (pressureFromUserArrayList.get(i).getId()==ID) {
                result.add(new PressureFromUser(pressureFromUserArrayList.get(i).getDate(), pressureFromUserArrayList.get(i).getUserName(), pressureFromUserArrayList.get(i).getPressure(), pressureFromUserArrayList.get(i).getId()));
            }
        }
        return result;
    }

    public ArrayList<WindSpeedFromUser> showWindSpeedFromUsersInCity(int ID, ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList) {
        ArrayList<WindSpeedFromUser> result = new ArrayList<>();
        for (int i = 0; i < windSpeedFromUserArrayList.size(); i++) {
            if (windSpeedFromUserArrayList.get(i).getID()==ID) {
                result.add(new WindSpeedFromUser(windSpeedFromUserArrayList.get(i).getDate(), windSpeedFromUserArrayList.get(i).getUserName(), windSpeedFromUserArrayList.get(i).getWindSpeed(), windSpeedFromUserArrayList.get(i).getID()));
            }
        }
        return result;
    }

    public ArrayList<HumidityFromUser> showHumidityFromUsersInCity(int ID, ArrayList<HumidityFromUser> humidityFromUserArrayList) {
        ArrayList<HumidityFromUser> result = new ArrayList<>();
        for (int i = 0; i < humidityFromUserArrayList.size(); i++) {
            if (humidityFromUserArrayList.get(i).getId()==ID) {
                result.add(new HumidityFromUser(humidityFromUserArrayList.get(i).getDate(), humidityFromUserArrayList.get(i).getUserName(), humidityFromUserArrayList.get(i).getHumidity(), humidityFromUserArrayList.get(i).getId()));
            }
        }
        return result;
    }

    public ArrayList<ClaudinessFromUser> showClaudinessFromUsersInCity(int ID, ArrayList<ClaudinessFromUser> claudinessFromUserArrayList) {
        ArrayList<ClaudinessFromUser> result = new ArrayList<>();
        for (int i = 0; i < claudinessFromUserArrayList.size(); i++) {
            if (claudinessFromUserArrayList.get(i).getId()==ID) {
                result.add(new ClaudinessFromUser(claudinessFromUserArrayList.get(i).getDate(), claudinessFromUserArrayList.get(i).getUserName(), claudinessFromUserArrayList.get(i).getClaudiness(), claudinessFromUserArrayList.get(i).getId()));
            }
        }
        return result;
    }


}
