package ViewControll;

import Repositories.FromDB.UserSettingsRepository;
import javafx.scene.control.TextField;

public class UserSettingsView {
    public UserSettingsView (UserSettingsRepository userSettingsRepository,TextField maxTemp, TextField minTemp,
                             TextField minWind, TextField maxWind, TextField minPressure, TextField maxPressure){
            maxTemp.setText(String.valueOf(userSettingsRepository.getMaxTemperature()));
            minTemp.setText(String.valueOf(userSettingsRepository.getMinTemperature()));
            maxWind.setText(String.valueOf(userSettingsRepository.getMaxWindSpeed()));
            minWind.setText(String.valueOf(userSettingsRepository.getMinWindSpeed()));
            maxPressure.setText(String.valueOf(userSettingsRepository.getMaxPressure()));
            minPressure.setText(String.valueOf(userSettingsRepository.getMinPressure()));
        }
    }


