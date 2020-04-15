package OWM;

import net.aksingh.owmjapis.api.APIException;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class WeatherMeasuresFactory {
    private ArrayList<WeatherMeasureOWM> weatherMeasuresListOWM =new ArrayList<>();
    private DataFromOWM dataFromOWM;
    private int howManyMeasures;
    public WeatherMeasuresFactory(String city, int howManyMeasures) throws FileNotFoundException, APIException, ParseException {
        this.howManyMeasures=howManyMeasures;
        dataFromOWM=new DataFromOWM(city);
        fillMeasuresist();
    }

    public void fillMeasuresist() throws FileNotFoundException, APIException, ParseException {
        for(int i=0 ;i<=howManyMeasures;i++) {

            weatherMeasuresListOWM.add(i,new WeatherMeasureOWM(dataFromOWM.tempInCity(i), dataFromOWM.windSpeedInCity(i), dataFromOWM.humidityInCity(i), dataFromOWM.pressureInCity(i),dataFromOWM.claudinessInCity(i), dataFromOWM.dateOfMeasure(i)));

        }
        }

    public ArrayList<WeatherMeasureOWM> getWeatherMeasuresListOWM() {
        return weatherMeasuresListOWM;
    }
}
