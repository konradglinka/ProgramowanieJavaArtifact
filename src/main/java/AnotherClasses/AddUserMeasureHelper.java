package AnotherClasses;

import Repositories.FromDB.AppSettingsRepository;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddUserMeasureHelper {

    StringBuilder stringBuilderWithAlertToLabel =new StringBuilder();
    private AppSettingsRepository appSettingsRepository;
    public AddUserMeasureHelper(AppSettingsRepository appSettingsRepository) {
        this.appSettingsRepository = appSettingsRepository;
    }
    public boolean veryficicationComplete(TextField pressureTextField, TextField temperatureTextField, TextField windTextField,
                                          TextField humidityTextField,Label addMesureAlertLabel) {
        boolean temperature= verificationTemperature(temperatureTextField);
        boolean windSpeed = verificationWindSpeed(windTextField);
        boolean pressure=verificationPressure(pressureTextField);
        boolean humidity=verificationHumidity(humidityTextField);
     if(temperatureTextField.getText().length()==0 && windTextField.getText().length()==0 && pressureTextField.getText().length()==0 && humidityTextField.getText().length()==0)
        {
            stringBuilderWithAlertToLabel.append("Nie można dodać pomiaru bez danych");
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText(stringBuilderWithAlertToLabel.toString());
            stringBuilderWithAlertToLabel.delete(0, stringBuilderWithAlertToLabel.length());
            return false; // Nie można dodać pomiaru bez danych
        }
        if(temperature==true&&windSpeed==true&&pressure==true&&humidity==true)
        {
            addMesureAlertLabel.setVisible(false);
            stringBuilderWithAlertToLabel.delete(0, stringBuilderWithAlertToLabel.length());
            return true;
        }
        else{
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText(stringBuilderWithAlertToLabel.toString());
            stringBuilderWithAlertToLabel.delete(0, stringBuilderWithAlertToLabel.length());
            return false;
        }
    }
    private boolean verificationTemperature(TextField temperatureTextField){

        boolean maxValue=false;
        boolean minValue=false;
        if (temperatureTextField.getText().equals(""))
        {
            temperatureTextField.setStyle("");
            return true;
        }
        try {
            Double temperature = Double.parseDouble(temperatureTextField.getText());
            if(temperature>= appSettingsRepository.getMinTemperature()){
                minValue=true;
            }
            if(temperature>= appSettingsRepository.getMaxTemperature()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(temperatureTextField)==true) {
                temperatureTextField.setStyle("");
                return true;
            }
            else{
                temperatureTextField.setStyle("-fx-background-color:red;");

                stringBuilderWithAlertToLabel.append("Podana temperatura nie mieści się w wartościach podanych w ustawieniach\n");
            return false;}
        }
        catch(Exception e)
        {
            temperatureTextField.setStyle("-fx-background-color:red;");

            stringBuilderWithAlertToLabel.append("Podana wartość temperatury jest nieprawidłowa\n");
            return false;
        }

    }
    private boolean verificationWindSpeed(TextField windSpeedTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (windSpeedTextField.getText().equals("")) {
            windSpeedTextField.setStyle("");
            return true;
        }
        try {
            Double windSpeed = Double.parseDouble(windSpeedTextField.getText());
            if(windSpeed>= appSettingsRepository.getMinWindSpeed()){
                minValue=true;
            }
            if(windSpeed>= appSettingsRepository.getMaxWindSpeed()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(windSpeedTextField)==true) {
                windSpeedTextField.setStyle("");
                return true;
            }
            else{
                windSpeedTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\n");
                return false;}
        }
        catch(Exception e)
        {
            windSpeedTextField.setStyle("-fx-background-color:red;");
            stringBuilderWithAlertToLabel.append("Podana prędkość wiatru jest nieprawidłowa\n");
            return false;
        }

    }
   private boolean verificationPressure(TextField pressureTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (pressureTextField.getText().equals("")) {
            pressureTextField.setStyle("");
            return true;
        }
        try {
            Double pressure = Double.parseDouble(pressureTextField.getText());
            if(pressure>= appSettingsRepository.getMinPressure()){
                minValue=true;
            }
            if(pressure>= appSettingsRepository.getMaxPressure()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(pressureTextField)==true) {
                pressureTextField.setStyle("");

                return true;
            }
            else{
                pressureTextField.setStyle("-fx-background-color:red;");

                stringBuilderWithAlertToLabel.append("Podane ćiśnienie powietrza nie mieści się w wartościach podanych w ustawieniach\n");
                return false;}
        }
        catch(Exception e)
        {
            pressureTextField.setStyle("-fx-background-color:red;");
            stringBuilderWithAlertToLabel.append("Podana wartość ćisnienia jest nieprawidłowa\n");
            return false;
        }

    }
    private boolean verificationHumidity(TextField humidityTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (humidityTextField.getText().equals("")) {
            humidityTextField.setStyle("");
            return true;
        }
        try {
            Double humidity = Double.parseDouble(humidityTextField.getText());
            if(humidity>= appSettingsRepository.getMinHumidity()){
                minValue=true;
            }
            if(humidity>= appSettingsRepository.getMaxHumidity()){
                maxValue=true;
            }
            if(minValue==true && maxValue==false && allCharsAreDigits(humidityTextField)==true) {
                humidityTextField.setStyle("");
                return true;
            }
            else{
                humidityTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podana wilgotność musi mieścić się w przedziale procentowym od 0 do 100\n");
                return false;}
        }
        catch(Exception e)
        {
            humidityTextField.setStyle("-fx-background-color:red;");
            stringBuilderWithAlertToLabel.append("Podana wartosć wilgotności jest nieprawidłowa\n");
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
