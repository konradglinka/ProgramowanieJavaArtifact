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
                                          TextField humidityTextField, Label addMeasureAlertLabel) {
        boolean temperature= verificationTemperature(temperatureTextField,addMeasureAlertLabel);
        boolean windSpeed = verificationWindSpeed(windTextField,addMeasureAlertLabel);
        boolean pressure=verificationPressure(pressureTextField,addMeasureAlertLabel);
        boolean humidity=verificationHumidity(humidityTextField,addMeasureAlertLabel);
        if(temperature==true&&windSpeed==true&&pressure==true&&humidity==true)
        {
            addMeasureAlertLabel.setVisible(false);
            return true;
        }
        //OBSLUGA PRZYPADKOW Z DWOMA ALERTAMI
        if(temperature==false&&windSpeed==false&&pressure==true&&humidity==true)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura nie mieści się w wartościach podanych w ustawieniach\nPodana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==false&&windSpeed==true&&pressure==false&&humidity==true)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==false&&windSpeed==true&&pressure==true&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==true&&windSpeed==false&&pressure==false&&humidity==true)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==true&&windSpeed==false&&pressure==true&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==true&&windSpeed==true&&pressure==false&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podane ciśnienie powietrza nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        //OBSLUGA PRZYPADKOW Z TRZEMA ALERTAMI
        if(temperature==true&&windSpeed==false&&pressure==false&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==false&&windSpeed==true&&pressure==false&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura powietrza nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==false&&windSpeed==false&&pressure==true&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura powietrza nie mieści się w wartościach podanych w ustawieniach\nPodana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        if(temperature==false&&windSpeed==false&&pressure==false&&humidity==true)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura powietrza nie mieści się w wartościach podanych w ustawieniach\nPodana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        //OBSLUGA PRZYPADKU Z CZTEREMA ALERTAMI
        if(temperature==false&&windSpeed==false&&pressure==false&&humidity==false)
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana temperatura powietrza nie mieści się w wartościach podanych w ustawieniach\nPodana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach\nPodane ciśnienie powietrza nie mieści się w wartościach podanych w ustawieniach\nPodana wilgotność powietrza nie mieści się w wartościach podanych w ustawieniach");
            return false;
        }
        //OBSLUGA PRZYPADKU BEZ DANYCH
        else if(temperatureTextField.getText().length()==0 && windTextField.getText().length()==0 && pressureTextField.getText().length()==0 && humidityTextField.getText().length()==0 )
        {
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Nie można dodać pomiaru bez danych");
            return false; // Nie można dodać pomiaru bez danych
        }
        return false;
    }
    private boolean verificationTemperature(TextField temperatureTextField, Label addMeasureAlertLabel){

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
                addMeasureAlertLabel.setVisible(true);
                addMeasureAlertLabel.setText("Podana temperatura nie mieści się w wartościach podanych w ustawieniach");
            return false;}
        }
        catch(Exception e)
        {
            temperatureTextField.setStyle("-fx-background-color:red;");
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana wartość temperatury jest nieprawidłowa");
            return false;
        }

    }
    private boolean verificationWindSpeed(TextField windSpeedTextField, Label addMeasureAlertLabel){

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
                addMeasureAlertLabel.setVisible(true);
                addMeasureAlertLabel.setText("Podana prędkość wiatru nie mieści się w wartościach podanych w ustawieniach");
                return false;}
        }
        catch(Exception e)
        {
            windSpeedTextField.setStyle("-fx-background-color:red;");
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana prędkość wiatru jest nieprawidłowa");
            return false;
        }

    }
   private boolean verificationPressure(TextField pressureTextField, Label addMeasureAlertLabel){

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
                addMeasureAlertLabel.setVisible(true);
                addMeasureAlertLabel.setText("Podane ćiśnienie powietrza nie mieści się w wartościach podanych w ustawieniach");
                return false;}
        }
        catch(Exception e)
        {
            pressureTextField.setStyle("-fx-background-color:red;");
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana wartość ćisnienia jest nieprawidłowa");
            return false;
        }

    }

    private boolean verificationHumidity(TextField humidityTextField, Label addMeasureAlertLabel){

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
                humidityTextField.setStyle("-fx-background-color:red;");
                addMeasureAlertLabel.setVisible(true);
                addMeasureAlertLabel.setText("Podana wilgotność musi mieścić się w przedziale procentowym od 0 do 100");
                return false;}
        }
        catch(Exception e)
        {
            humidityTextField.setStyle("-fx-background-color:red;");
            addMeasureAlertLabel.setVisible(true);
            addMeasureAlertLabel.setText("Podana wartosć wilgotności jest nieprawidłowa");
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
