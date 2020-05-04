/*
package MeasuresFromUsers;

import java.awt.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckUserSettings {

    public boolean verificationSettings(TextField settingsMaxTemperatureTextField, TextField settingsMinTemperatureTextField,
                                        TextField settingsMaxPressureTextField, TextField settingsMinPressureTextField,
                                        TextField settingsMaxWindSpeedTextField, TextField settingsMinWindSpeedTextField,
                                        Label settingsAlertLabel){
        if(allCharsAreDigits(settingsMaxTemperatureTextField)==true && allCharsAreDigits(settingsMinTemperatureTextField)==true
        && allCharsAreDigits(settingsMaxPressureTextField)==true && allCharsAreDigits(settingsMinPressureTextField)==true
        && allCharsAreDigits(settingsMaxWindSpeedTextField)==true && allCharsAreDigits(settingsMinWindSpeedTextField)==true){
            settingsAlertLabel.setVisible(false);
            return true;
        }
        else if(settingsMaxTemperatureTextField.getText().length()==0 && settingsMinTemperatureTextField.getText().length()==0
                && settingsMaxPressureTextField.getText().length()==0 && settingsMinPressureTextField.getText().length()==0
                && settingsMaxWindSpeedTextField.getText().length()==0 && settingsMinWindSpeedTextField.getText().length()==0)
        {
            settingsAlertLabel.setVisible(true);
            settingsAlertLabel.setText("Nie można zapisać ustawień bez danych");
            return false; // Nie można zapisać pomiaru bez danych
        }
        return false;
    }
    private boolean allCharsAreDigits (TextField mesureTextField){
        for(int i=0; i<mesureTextField.getText().length();i++) {
            if (!((int) (mesureTextField.getText().charAt(i)) >= 48 && (int) (mesureTextField.getText().charAt(i)) <= 57)) {
                return false;
            }
        }
        return true;
    }
}
*/