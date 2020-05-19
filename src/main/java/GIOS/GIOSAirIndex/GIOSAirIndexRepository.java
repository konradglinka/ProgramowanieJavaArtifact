package GIOS.GIOSAirIndex;

import AnotherClasses.JSONReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GIOSAirIndexRepository {
    public ArrayList<AirIndexLevel> getAirIndexData(int IDStation) throws IOException, JSONException {
        JSONReader jsonReader=new JSONReader();
        ArrayList<AirIndexLevel>airIndexLevelList=new ArrayList<>();
        ObjectMapper mapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<GIOSPLAirIndex> GIOSAirIndexList = mapper.readValue(jsonReader.readJsonFromUrl("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + IDStation), new TypeReference<List<GIOSPLAirIndex>>() {
        });
        try {
            airIndexLevelList.add(new AirIndexLevel("Sumarycznie",GIOSAirIndexList.get(0).getStIndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        try {
            airIndexLevelList.add(new AirIndexLevel("C6H6",GIOSAirIndexList.get(0).getC6h6IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        try {
            airIndexLevelList.add(new AirIndexLevel("CO",GIOSAirIndexList.get(0).getCoIndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }

        try {
            airIndexLevelList.add(new AirIndexLevel("NO2", GIOSAirIndexList.get(0).getNo2IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        try {
            airIndexLevelList.add(new AirIndexLevel("O3",GIOSAirIndexList.get(0).getO3IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        try {
            airIndexLevelList.add(new AirIndexLevel("PM2,5",GIOSAirIndexList.get(0).getPm25IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        try {
            airIndexLevelList.add(new AirIndexLevel("PM10",GIOSAirIndexList.get(0).getPm10IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }

        try {
            airIndexLevelList.add(new AirIndexLevel("SO2",GIOSAirIndexList.get(0).getSo2IndexLevel().getIndexLevelName()));
        }
        catch (NullPointerException e){

        }
        return airIndexLevelList;
}
}
