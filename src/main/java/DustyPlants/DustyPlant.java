package DustyPlants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DustyPlant {

    private String name;
    private int startDustMonth;
    private int endDustMonth;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DustyPlant(String name, int startDustMonth, int endDustMonth) {
        this.name = name;
        this.startDustMonth = startDustMonth;
        this.endDustMonth = endDustMonth;
    }

    public int getStartDustMonth() {
        return startDustMonth;
    }



    public int getEndDustMonth() {
        return endDustMonth;
    }


}
