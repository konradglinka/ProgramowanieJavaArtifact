package AnotherClasses;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterHelper {

    public boolean isPasswordStrength(PasswordField passwordField, Label alertLabel){ //Funkcja sprawdza czy wpisane hasło jest silne
        String password=passwordField.getText();
        boolean smallAndHighLetters=true;
        boolean minimalSizeOfPassword=false;
        boolean maximalSizeOfPassword=false;
        boolean haveLetter = false;
        boolean haveNumber= false;
        boolean specialMark=false;
        if(password.equals(password.toLowerCase())){
            smallAndHighLetters=false;
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi zawierać duże i małe litery");
        }
        if(password.equals(password.toUpperCase())){
            smallAndHighLetters=false;
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi zawierać duże i małe litery");
        }
        if(password.length()>=8){
            minimalSizeOfPassword=true;

        }
        else {
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi mieć minimum 8 znaków");
        }
        if(password.length()<=35){
            maximalSizeOfPassword=false;

        }
        else {
            maximalSizeOfPassword=true;
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło może mieć maksymalnie 35 znaków");
        }
        password=password.toUpperCase();
        for(int i=0; i<password.length();i++) {
            if ((int) (password.charAt(i)) >= 65 && (int) (password.charAt(i)) <= 90) {
                haveLetter=true;

            } else if ((int) (password.charAt(i)) >= 48 && (int) (password.charAt(i)) <= 57) {
                haveNumber=true;
            }
            else
            {
                specialMark=true;
            }


        }
        if(specialMark==false){
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi zawierać znak specjalny");
        }
        if(haveLetter==false) {
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi zawierać małe i duże litery");
        }
        if(haveNumber==false){
            alertLabel.setVisible(true);
            alertLabel.setText("Hasło musi zawierać cyfry");
        }

        if(specialMark==true && minimalSizeOfPassword==true && maximalSizeOfPassword==false && smallAndHighLetters==true)
        {
            alertLabel.setVisible(false);
            return true;
        }
        return false;
    }

    public boolean isEmail(TextField emailTextField){
        String email= emailTextField.getText();
        boolean haveMonkey=false;
        boolean havePoint=false;
        boolean minimalSize=false;
        boolean maximalSize=false;
        boolean pointIsAfterMonkey=false;
        boolean startWithMonkey=false;
        boolean lettersBeetwenMonkeyAndPoint=false;
        boolean textAfterPoint=true;
        int whereIsMonkey=0;
        int whereIsPoint=0;
        if (email.length()>=5)
        {
            minimalSize=true;
        }
        if (email.length()>40)
        {
            maximalSize=true;
        }
        for(int i=0;i<email.length();i++){

            if(email.charAt(i)=='@')
            {

                haveMonkey=true;
                whereIsMonkey=i;

            }
            if(email.charAt(i)=='.')
            {
                havePoint=true;
                whereIsPoint=i;

            }
        }
        if(whereIsMonkey<whereIsPoint){
            pointIsAfterMonkey=true;

        }
        if(whereIsPoint-whereIsMonkey>1)

        {

            lettersBeetwenMonkeyAndPoint=true;
        }
        if(whereIsMonkey==0){

            startWithMonkey=true;
        }
        if(email.endsWith(".")){
            textAfterPoint=false;

        }

        if(haveMonkey==true && havePoint==true && minimalSize==true && maximalSize==false && pointIsAfterMonkey==true &&startWithMonkey==false&&lettersBeetwenMonkeyAndPoint==true&&textAfterPoint==true)
        {
            return true;
        }
        return false;
    }

}
