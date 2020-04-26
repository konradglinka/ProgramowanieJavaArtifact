package MeasuresFromUsers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class CheckDataBeforeAddMesure {
  private UserSettings userSettings=new UserSettings();

    public UserSettings getUserSettings() {
        return userSettings;
    }


    public boolean veryficicationComplete(TextField pressureTextField, TextField temperatureTextField, TextField windTextField,
                                          TextField humidityTextField, Label addMesureAlertLabel ) {
        boolean temperature= verificationTemperature(temperatureTextField,addMesureAlertLabel);
        boolean windSpeed = verificationWindSpeed(windTextField,addMesureAlertLabel);
        boolean pressure=verificationPressure(pressureTextField,addMesureAlertLabel);
        boolean humidity=verificationHumidity(humidityTextField,addMesureAlertLabel);
        if(temperature==true&&windSpeed==true&&pressure==true&&humidity==true)
        {
            addMesureAlertLabel.setVisible(false);
            return true;
        }

        else if(temperatureTextField.getText().length()==0 && windTextField.getText().length()==0 && pressureTextField.getText().length()==0 && humidityTextField.getText().length()==0 )
        {
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText("Nie można dodać pomiaru bez danych");
            return false; // Nie można dodać pomiaru bez danych
        }
        return false;
    }
    private boolean verificationTemperature(TextField temperatureTextField, Label addMesureAlertLabel){

        boolean maxValue=false;
        boolean minValue=false;
        if (temperatureTextField.getText().equals(""))
        {
            temperatureTextField.setStyle("");
            return true;
        }
        try {
            Double temperature = Double.parseDouble(temperatureTextField.getText());
            if(temperature>=userSettings.getMinTemperature()){
                minValue=true;
            }
            if(temperature>=userSettings.getMaxTemperature()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(temperatureTextField)==true) {
                temperatureTextField.setStyle("");


                return true;
            }
            else{
                temperatureTextField.setStyle("-fx-background-color:red;");
                addMesureAlertLabel.setVisible(true);
                addMesureAlertLabel.setText("Podana temperatura nie mieści się w wartościach podanych w ustawieniach");
            return false;}
        }
        catch(Exception e)
        {
            temperatureTextField.setStyle("-fx-background-color:red;");
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText("Podana wartość temperatury jest nieprawidłowa");
            return false;
        }

    }
    private boolean verificationWindSpeed(TextField windSpeedTextField, Label addMesureAlertLabel){

        boolean maxValue=false;
        boolean minValue=false;
        if (windSpeedTextField.getText().equals("")) {
            windSpeedTextField.setStyle("");
            return true;
        }
        try {
            Double windSpeed = Double.parseDouble(windSpeedTextField.getText());
            if(windSpeed>=userSettings.getMinWindSpeed()){
                minValue=true;
            }
            if(windSpeed>=userSettings.getMaxWindSpeed()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(windSpeedTextField)==true) {
                windSpeedTextField.setStyle("");

                return true;
            }
            else{
                windSpeedTextField.setStyle("-fx-background-color:red;");
                addMesureAlertLabel.setVisible(true);
                addMesureAlertLabel.setText("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach");
                return false;}
        }
        catch(Exception e)
        {
            windSpeedTextField.setStyle("-fx-background-color:red;");
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText("Podana prędkość wiatru jest nieprawidłowa");
            return false;
        }

    }
   private boolean verificationPressure(TextField pressureTextField, Label addMesureAlertLabel){

        boolean maxValue=false;
        boolean minValue=false;
        if (pressureTextField.getText().equals("")) {
            pressureTextField.setStyle("");
            return true;
        }
        try {
            Double pressure = Double.parseDouble(pressureTextField.getText());
            if(pressure>=userSettings.getMinPressure()){
                minValue=true;
            }
            if(pressure>=userSettings.getMaxPressure()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(pressureTextField)==true) {
                pressureTextField.setStyle("");

                return true;
            }
            else{
                pressureTextField.setStyle("-fx-background-color:red;");
                addMesureAlertLabel.setVisible(true);
                addMesureAlertLabel.setText("Podane ćiśnienie powietrza nie mieści się w wartościach podanych w ustawieniach");
                return false;}
        }
        catch(Exception e)
        {
            pressureTextField.setStyle("-fx-background-color:red;");
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText("Podana wartość ćisnienia jest nieprawidłowa");
            return false;
        }

    }

    private boolean verificationHumidity(TextField humidityTextField, Label addMesureAlertLabel){

        boolean maxValue=false;
        boolean minValue=false;
        if (humidityTextField.getText().equals("")) {
            humidityTextField.setStyle("");
            return true;
        }
        try {
            Double humidity = Double.parseDouble(humidityTextField.getText());
            if(humidity>=userSettings.getMinHumidity()){
                minValue=true;
            }
            if(humidity>=userSettings.getMaxHumidity()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(humidityTextField)==true) {
                humidityTextField.setStyle("");
                return true;
            }
            else{
                humidityTextField.setStyle("-fx-background-color:red;"); addMesureAlertLabel.setVisible(true);
                addMesureAlertLabel.setText("Podana wilgotność musi mieścić się w przedziale procentowym od 0 do 100");
                return false;}
        }
        catch(Exception e)
        {
            humidityTextField.setStyle("-fx-background-color:red;");
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText("Podana wartosć wilgotności jest nieprawidłowa");
            return false;
        }

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
