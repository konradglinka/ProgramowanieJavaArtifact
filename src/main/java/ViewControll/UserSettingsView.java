package ViewControll;

import Repositories.FromDB.AppSettingsRepository;
import javafx.scene.control.TextField;

public class UserSettingsView {
    public UserSettingsView (AppSettingsRepository appSettingsRepository, TextField maxTemp, TextField minTemp,
                             TextField minWind, TextField maxWind, TextField minPressure, TextField maxPressure){
            maxTemp.setText(String.valueOf(appSettingsRepository.getAppSettings().getMaxTemperature()));
            minTemp.setText(String.valueOf(appSettingsRepository.getAppSettings().getMinTemperature()));
            maxWind.setText(String.valueOf(appSettingsRepository.getAppSettings().getMaxWindSpeed()));
            minWind.setText(String.valueOf(appSettingsRepository.getAppSettings().getMinWindSpeed()));
            maxPressure.setText(String.valueOf(appSettingsRepository.getAppSettings().getMaxPressure()));
            minPressure.setText(String.valueOf(appSettingsRepository.getAppSettings().getMinPressure()));
        }
    }


