package MeasuresFromUsers;


import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class VerificationDataFromUser {
    public boolean verificationTemperature(TextField temperatureTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (temperatureTextField.getText().equals(""))
            return true;
        try {
            Double temperature = Double.parseDouble(temperatureTextField.getText());
            if(temperature>-50.0){
                minValue=true;
            }
            if(temperature>60.0){
                maxValue=true;
            }
            if(minValue==true && maxValue==false) {
                temperatureTextField.setStyle("");
                return true;
            }
            else{
                temperatureTextField.setStyle("-fx-background-color:red;");
            return false;}
        }
        catch(Exception e)
        {
            temperatureTextField.setStyle("-fx-background-color:red;");
            return false;
        }

    }
    public boolean verificationWindSpeed(TextField windSpeedTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (windSpeedTextField.getText().equals(""))
            return true;
        try {
            Double windSpeed = Double.parseDouble(windSpeedTextField.getText());
            if(windSpeed>0.0){
                minValue=true;
            }
            if(windSpeed>63.0){
                maxValue=true;
            }
            if(minValue==true && maxValue==false) {
                windSpeedTextField.setStyle("");
                return true;
            }
            else{
                windSpeedTextField.setStyle("-fx-background-color:red;");
                return false;}
        }
        catch(Exception e)
        {
            windSpeedTextField.setStyle("-fx-background-color:red;");
            return false;
        }

    }
    public boolean verificationPressure(TextField pressureTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (pressureTextField.getText().equals(""))
            return true;
        try {
            Double pressure = Double.parseDouble(pressureTextField.getText());
            if(pressure>870.0){
                minValue=true;
            }
            if(pressure>1086.0){
                maxValue=true;
            }
            if(minValue==true && maxValue==false) {
                pressureTextField.setStyle("");
                return true;
            }
            else{
                pressureTextField.setStyle("-fx-background-color:red;");
                return false;}
        }
        catch(Exception e)
        {
            pressureTextField.setStyle("-fx-background-color:red;");
            return false;
        }

    }
    public boolean verificationClaudiness(TextField claudinessTextField){
        boolean haveNumber=false;
        if (claudinessTextField.getText().equals(""))
            return true;
        try {
            String claudiness = claudinessTextField.getText();
            for(int i =0; i<claudiness.length();i++) {
                if ((int) (claudiness.charAt(i)) >= 48 && (int) (claudiness.charAt(i)) <= 57) {
                    haveNumber = true;
                }
            }
            if(haveNumber==false) {
                claudinessTextField.setStyle("");
                return true;
            }
            else{
                claudinessTextField.setStyle("-fx-background-color:red;");
                return false;}
        }
        catch(Exception e)
        {
            claudinessTextField.setStyle("-fx-background-color:red;");
            return false;
        }

    }
    public boolean verificationHumidity(TextField humidityTextField){
        boolean maxValue=false;
        boolean minValue=false;
        if (humidityTextField.getText().equals(""))
            return true;
        try {
            Double humidity = Double.parseDouble(humidityTextField.getText());
            if(humidity>0.0){
                minValue=true;
            }
            if(humidity>100.0){
                maxValue=true;
            }
            if(minValue==true && maxValue==false) {
                humidityTextField.setStyle("");
                return true;
            }
            else{
                humidityTextField.setStyle("-fx-background-color:red;");
                return false;}
        }
        catch(Exception e)
        {
            humidityTextField.setStyle("-fx-background-color:red;");
            return false;
        }

    }

}
