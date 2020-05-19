package OWM;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;


import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFromOWM {
    private OWM owm = new OWM("9893d21085d2622338d6efc7d92ed555");
    net.aksingh.owmjapis.model.HourlyWeatherForecast dailyWeatherForecast;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy       HH:mm");
    public DataFromOWM(int id) throws FileNotFoundException, APIException {
        owm.setUnit(OWM.Unit.METRIC); //Temperatura w stopniach celsjusza
   dailyWeatherForecast = owm.hourlyWeatherForecastByCityId(id);
    }
    public double maxTempInCity(int timeOfMeasure)  { //funkcja zwraca maksymalną temperature w mieście

        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getMainData().getTempMax();
    }
    public double tempInCity(int timeOfMeasure)  { //funkcja zwraca minimalną temperature w miescie

        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getMainData().getTemp();
    }
    public double windSpeedInCity(int timeOfMeasure) { //funkcja zwraca prędkość wiatru

        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getWindData().getSpeed();
    }
    public double humidityInCity(int timeOfMeasure)  { //funkcja zwraca wilgotność powietrza

        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getMainData().getHumidity();
    }
    public double pressureInCity(int timeOfMeasure)  { //funkcja zwraca wilgotność powietrza

        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getMainData().getPressure();
    }
    public String dateOfMeasure(int timeOfMeasure)  { //funkcja zwraca date kiedy wykonano pomiar
        Date date =dailyWeatherForecast.getDataList().get(timeOfMeasure).getDateTime();

        return dateFormat.format(date);
    }
    public String claudinessInCity(int timeOfMeasure) { //funkcja zwraca stan nieba w mieście (chmury,deszcz itp)
        return dailyWeatherForecast.getDataList().get(timeOfMeasure).getWeatherList().get(0).component3();
    }




}
