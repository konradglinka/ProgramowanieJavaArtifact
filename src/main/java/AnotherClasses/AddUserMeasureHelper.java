package AnotherClasses;

import Repositories.FromDB.AppSettingsRepository;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddUserMeasureHelper {

    StringBuilder stringBuilderWithAlertToLabel =new StringBuilder(); //Zawiera komunikat o nieprawidłowym pomiarze
    private AppSettingsRepository appSettingsRepository; //Przechowuje aktualne ustawienia wartości granicznych
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
            return true; //Jeśli  przeszło weryfikacje  dodajemy pomiar
        }
        else{
            addMesureAlertLabel.setVisible(true);
            addMesureAlertLabel.setText(stringBuilderWithAlertToLabel.toString());
            stringBuilderWithAlertToLabel.delete(0, stringBuilderWithAlertToLabel.length());
            return false; //Jeśli nie przeszło weryfikacji nie dodajemy pomiaru i wyświetlamy alert
        }
    }
    private boolean verificationTemperature(TextField temperatureTextField){
        if (temperatureTextField.getText().equals(""))
        {
            temperatureTextField.setStyle("");
            return true;
        }
            Double temperature = Double.parseDouble(temperatureTextField.getText());
            if(temperature>= appSettingsRepository.getAppSettings().getMinTemperature() && temperature>= appSettingsRepository.getAppSettings().getMaxTemperature()&& allCharsAreDigits(temperatureTextField)==true) {
                temperatureTextField.setStyle("");
                return true;
            }
            else{
                temperatureTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podana temperatura nie mieści się w wartościach granicznych \n");
            return false;}
    }
    private boolean verificationWindSpeed(TextField windSpeedTextField){
        if (windSpeedTextField.getText().equals("")) {
            windSpeedTextField.setStyle("");
            return true;
        }
            Double windSpeed = Double.parseDouble(windSpeedTextField.getText());
            if(windSpeed>= appSettingsRepository.getAppSettings().getMinWindSpeed()&&windSpeed<= appSettingsRepository.getAppSettings().getMaxWindSpeed()&& allCharsAreDigits(windSpeedTextField)==true) {
                windSpeedTextField.setStyle("");
                return true;
            }
            else{
                windSpeedTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podana prędkość wiatru nie mieści się w wartościach granicznych \n");
                return false;}
    }
   private boolean verificationPressure(TextField pressureTextField){
        if (pressureTextField.getText().equals("")) {
            pressureTextField.setStyle("");
            return true;
        }
            Double pressure = Double.parseDouble(pressureTextField.getText());
            if(pressure>= appSettingsRepository.getAppSettings().getMinPressure() && pressure>= appSettingsRepository.getAppSettings().getMaxPressure() && allCharsAreDigits(pressureTextField)==true) {
                pressureTextField.setStyle("");
                return true;
            }
            else{
                pressureTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podane ćiśnienie powietrza nie mieści się w wartościach podanych w ustawieniach\n");
                return false;}
    }
    private boolean verificationHumidity(TextField humidityTextField){
        if (humidityTextField.getText().equals("")) {
            humidityTextField.setStyle("");
            return true;
        }
            Double humidity = Double.parseDouble(humidityTextField.getText());
            if(humidity>= appSettingsRepository.getAppSettings().getMinHumidity() && humidity>= appSettingsRepository.getAppSettings().getMaxHumidity()&& allCharsAreDigits(humidityTextField)==true) {
                humidityTextField.setStyle("");
                return true;
            }
            else{
                humidityTextField.setStyle("-fx-background-color:red;");
                stringBuilderWithAlertToLabel.append("Podana wilgotność musi mieścić się w przedziale procentowym od 0 do 100\n");
                return false;}
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
