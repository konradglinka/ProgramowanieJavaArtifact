import MeasuresFromUsers.MesureFromUser;
import MeasuresFromUsers.TypeOfMeasure.*;
import MeasuresFromUsers.VerificationDataFromUser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Calendar.getInstance;

public class JDBCQuery { //Klasa zawiera metody współpracujące z bazą danych
    VerificationDataFromUser verificationDataFromUser = new VerificationDataFromUser();
   AnotherFunctions anotherFunctions=new AnotherFunctions();
    private String userNameToMeasures = "USER"; //Nazwa użytkownika do testów to USER w przypadku braku podania email
    ArrayList<MesureFromUser> listOfMeasures = new ArrayList<>(); //Lista wszystkich pomiarów od użytkownika
    private static Connection connection; //Polaczenie z baza danych
    public JDBCQuery(JDBC jdbc) throws SQLException { //Laczymy sie z baza
        connection = jdbc.getConnection();
        getMeasureFromUserListFromDataBase();

    }

    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    //Funkcja odpowaida za logowanie
    public boolean loginCheck(TextField emailTextField, PasswordField passwordFromUser) throws SQLException {
        ResultSet resultSet = null;
        String email = emailTextField.getText();
        String password = anotherFunctions.getMD5Password(passwordFromUser.getText());
        String loginQuerySQL = "SELECT EMAIL,PASSWORD from users " +
                "WHERE EMAIL = '" + email + "' AND PASSWORD ='" + password + "'";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        try {
            resultSet = stmt.executeQuery(loginQuerySQL);
        } catch (SQLException e) {
            System.out.println("ERROR:Bad SQL query");
        }
        if (resultSet.next()) {
            userNameToMeasures = email;
            return true;
        }
        return false;
    }

    //Funkcja odpowiada za rejestracje użytkownika
    public boolean addNewUser(TextField emailTextField, PasswordField passwordFromUser, PasswordField confirmedPasswordFromUser,
                              Label registrationAlertLabel) throws SQLException {
        String email = emailTextField.getText();
        String password = anotherFunctions.getMD5Password(passwordFromUser.getText());


                        String addUserQuerySQL =
                                "INSERT INTO users (EMAIL,PASSWORD) VALUES ('" + email + "', '" + password + "')";
                        Statement stmt = null;
                        try {
                            stmt = connection.createStatement();
                        } catch (SQLException e) {
                            System.out.println("ERROR:No connection with Database");
                        }
                        try {
                            stmt.executeUpdate(addUserQuerySQL);
                            registrationAlertLabel.setVisible(false);
                            return true;
                        } catch (SQLException e) {
                            System.out.println("ERROR:Bad SQL query");
                        }
                  return false;
        }

    //Funkcja pomocnicza sprawdza czy adres email nie dubluje się w bazie
   public boolean isAccountAlreadyEmailInDataBase(String email) throws SQLException {
        ResultSet resultSet = null;


        String findEmailInDataBase = "SELECT EMAIL from users WHERE EMAIL = '" + email + "'";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        try {
            resultSet = stmt.executeQuery(findEmailInDataBase);
        } catch (SQLException e) {
            System.out.println("ERROR:Bad SQL query");
        }
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    //FUNKCJE DOTYCZĄCE POMIARÓW OD UŻYTKOWNIKA
    //Funkcja dodaje pomiar od użytkownika
    public void addMeasuresFromUserToDataBase(TextField pressureTextField, TextField temperatureTextField, TextField windTextField,
                                              TextField humidityTextField, TextField cloudinessTextField, ListView<String> cityListListView) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date actualDate = getInstance().getTime();
        String userName = userNameToMeasures;
        String date = dateFormat.format(actualDate);
        String temperature = temperatureTextField.getText();
        String windSpeed = windTextField.getText();
        String humidity = humidityTextField.getText();
        String claudiness = cloudinessTextField.getText();
        String pressure = pressureTextField.getText();
        String city = cityListListView.getSelectionModel().getSelectedItem();
        boolean allEmpty=false;
        System.out.println(temperature.length());
        if(temperature.length()==0 && windSpeed.length()==0
                && humidity.length()==0 && claudiness.length()==0 &&pressure.length()==0){
      
            allEmpty=true;
        }
        String addMeasureFromUserQuerySQL =
                "INSERT INTO measuresfromusers (DATE,USERNAME,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,CITY) VALUES " +
                        "('" + date + "', '" + userName + "', '" + temperature + "', '" + windSpeed + "', '" + humidity + "', '"
                        + claudiness + "', '" + pressure + "', '" + city + "')";
        Statement stmt = null;
        if(veryficicationComplete(pressureTextField,temperatureTextField,windTextField,
                humidityTextField, cloudinessTextField)==true&&allEmpty==false)  {
            try {
                stmt = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("ERROR:No connection with Database");
            }
            try {
                stmt.executeUpdate(addMeasureFromUserQuerySQL);

            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
        }
    }

    //Metoda pobiera pomiary od użytkowników
    public void getMeasureFromUserListFromDataBase() throws SQLException {
        listOfMeasures.clear(); //Usuwamy poprzednie pomiary
        //Metoda zwraca liste rekordow z bazy danych
        String takeAllMeasuresQuerySQL = "SELECT * FROM measuresfromusers";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeAllMeasuresQuerySQL);
        while (rs.next()) {
            listOfMeasures.add(new MesureFromUser(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getDouble(4), rs.getDouble(5),
                    rs.getDouble(6), rs.getString(7), rs.getDouble(8), rs.getString(9)));
        }

    }

    //TE FUNKCJE ZWRACAJĄ POMIARY DLA WSZYSTKICH MIAST ICH OBRÓBKĄ ZAJMUJE SIE TablesForCityFactory
    //Funkcja zwraca pomiary temperatury
    public ArrayList<TemperatureFromUser> getTemperaturesFromUserList() {
        ArrayList<TemperatureFromUser> temperaturesFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getTemperature() != 0) {
                temperaturesFromUserArrayList.add(new TemperatureFromUser(listOfMeasures.get(i).getDate(),
                        listOfMeasures.get(i).getUserName(),
                        listOfMeasures.get(i).getTemperature(),
                        listOfMeasures.get(i).getCity()));
            }
        }
        return temperaturesFromUserArrayList;
    }
    //Funkcja zwraca pomiaru predkości wiatru
    public ArrayList<WindSpeedFromUser> getWindSpeedFromUserList() {
        ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getWindSpeed() != 0) {
                windSpeedFromUserArrayList.add(new WindSpeedFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getWindSpeed(), listOfMeasures.get(i).getCity()));
            }
        }
        return windSpeedFromUserArrayList;
    }
//Funkcja zwraca pomiary wilgotności
    public ArrayList<HumidityFromUser> getHumidityFromUserList() {
        ArrayList<HumidityFromUser> humidityFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getHumidity() != 0) {
                humidityFromUserArrayList.add(new HumidityFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getHumidity(), listOfMeasures.get(i).getCity()));
            }
        }
        return humidityFromUserArrayList;
    }
//Funckja zwraca pomiary ciśnienia powietrza
    public ArrayList<PressureFromUser> getPressureFromUserList() {
        ArrayList<PressureFromUser> pressureFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getPressure() != 0) {
                pressureFromUserArrayList.add(new PressureFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getPressure(), listOfMeasures.get(i).getCity()));
            }
        }
        return pressureFromUserArrayList;
    }
//Funkcja zwraca pomiary zachmurzenia
    public ArrayList<ClaudinessFromUser> getClaudinessFromUserList() {
        ArrayList<ClaudinessFromUser> claudinessFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (!listOfMeasures.get(i).getClaudiness().equals("")) {
                claudinessFromUserArrayList.add(new ClaudinessFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getClaudiness(), listOfMeasures.get(i).getCity()));
            }
        }
        return claudinessFromUserArrayList;
    }


   public boolean isEmail(TextField emailTextField, Label alertLabel){
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

        alertLabel.setVisible(true);
        alertLabel.setText("Nie prawidłowy adres E-mail");
        return false;
    }
   public boolean isPasswordStrength(PasswordField passwordField,Label alertLabel){
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
    public boolean changeUserPassword(TextField emailToPasswordChange,PasswordField passwordFieldToChange,PasswordField confirmedPasswordFromUser,Label badPasswordLabel) throws SQLException {
        String password=anotherFunctions.getMD5Password(passwordFieldToChange.getText());
        String confirmedPassword = anotherFunctions.getMD5Password(confirmedPasswordFromUser.getText());
        String email=emailToPasswordChange.getText();
            if (isPasswordStrength(passwordFieldToChange, badPasswordLabel) == true) {

                if (password.equals(confirmedPassword)) {

                String changeUserPasswordQuerySQL = "UPDATE users SET `PASSWORD`='" + password + "' WHERE email='" + email + "'";
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                } catch (SQLException e) {
                    System.out.println("ERROR:No connection with Database");
                }
                try {
                    stmt.executeUpdate(changeUserPasswordQuerySQL);
                    return true;

                } catch (SQLException e) {
                    System.out.println("ERROR:Bad SQL query");
                }
            }
         }
            else if (!password.equals(confirmedPassword)) {
                badPasswordLabel.setVisible(true);
              badPasswordLabel.setText("Hasła nie są jednakowe");

            }
        return false;
    }
    private boolean veryficicationComplete(TextField pressureTextField, TextField temperatureTextField, TextField windTextField,
                                           TextField humidityTextField, TextField cloudinessTextField) {

        boolean temperature= verificationDataFromUser.verificationTemperature(temperatureTextField);
        boolean windSpeed = verificationDataFromUser.verificationWindSpeed(windTextField);
        boolean pressure=verificationDataFromUser.verificationPressure(pressureTextField);
        boolean claudiness=verificationDataFromUser.verificationClaudiness(cloudinessTextField);
        boolean humidity=verificationDataFromUser.verificationHumidity(humidityTextField);
        if(temperature==true&&windSpeed==true&&pressure==true&&claudiness==true&&humidity==true)
        {
            return true;
        }
        else {
            return false;
        }
    }
}

