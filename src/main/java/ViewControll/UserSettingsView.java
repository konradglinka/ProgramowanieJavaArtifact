package ViewControll;

import Repositories.FromDB.AppSettingsRepository;
import javafx.scene.control.TextField;

public class UserSettingsView {
    public UserSettingsView (AppSettingsRepository appSettingsRepository, TextField maxTemp, TextField minTemp,
                             TextField minWind, TextField maxWind, TextField minPressure, TextField maxPressure){
            maxTemp.setText(String.valueOf(appSettingsRepository.getMaxTemperature()));
            minTemp.setText(String.valueOf(appSettingsRepository.getMinTemperature()));
            maxWind.setText(String.valueOf(appSettingsRepository.getMaxWindSpeed()));
            minWind.setText(String.valueOf(appSettingsRepository.getMinWindSpeed()));
            maxPressure.setText(String.valueOf(appSettingsRepository.getMaxPressure()));
            minPressure.setText(String.valueOf(appSettingsRepository.getMinPressure()));
        }
    }


