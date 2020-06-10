package Repositories;

import ObjectsForMapper.GIOSData.GIOSData;
import AnotherClasses.JSONReader;
import Objects.SensorData;
import ObjectsForMapper.GIOSData.Values;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GIOSSensorDataRepository {
    JSONReader jsonReader=new JSONReader();
    ObjectMapper mapper = new ObjectMapper();

    public ArrayList<SensorData> getDataFromSensor(int IDSensor) throws IOException, JSONException, ParseException {
        ArrayList<SensorData>listOfValues=new ArrayList<>();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<GIOSData>GIOSDataList = mapper.readValue(jsonReader.readJsonFromUrl("http://api.gios.gov.pl/pjp-api/rest/data/getData/" + IDSensor), new TypeReference<List<GIOSData>>() {
        });
        Values[]tmp=GIOSDataList.get(0).getValues();
        for(int i=0; i<tmp.length;i++){
            if(!(tmp[i].getValue()==null)) {
                listOfValues.add(new SensorData(tmp[i].getDate().replace(" ", "         "),(double)(Math.round(Double.parseDouble(tmp[i].getValue())*100.00))/100.00));
            }
        }
        return listOfValues;
    }
}
