import MeasuresFromUsers.MesureFromUser;
import MeasuresFromUsers.TypeOfMeasure.*;
import MeasuresFromUsers.CheckDataBeforeAddMesure;
import MeasuresFromUsers.UserSettings;
import RegisterAndLoginActions.VerificateDataFromUser;
import javafx.scene.control.*;
import org.jetbrains.annotations.Nullable;

import javax.crypto.NullCipher;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static java.util.Calendar.getInstance;

public class JDBCQuery { //Klasa zawiera metody współpracujące z bazą danych

    private CheckDataBeforeAddMesure checkDataBeforeAddMesure = new CheckDataBeforeAddMesure();
    private UserSettings userSettings=checkDataBeforeAddMesure.getUserSettings();
    private VerificateDataFromUser verificateDataFromUser = new VerificateDataFromUser();
    private MD5 MD5 =new MD5();
    private String userNameToMeasures = "USER"; //Nazwa użytkownika do testów to USER w przypadku braku podania email
    private  ArrayList<MesureFromUser> listOfMeasures = new ArrayList<>(); //Lista wszystkich pomiarów od użytkownika
    private static Connection connection; //Polaczenie z baza danych

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public JDBCQuery(JDBC jdbc) throws SQLException { //Laczymy sie z baza
        connection = jdbc.getConnection();
       // getMeasureFromUserListFromDataBase();
    }
    public void getConnectionToDataBase(){

    }

    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    //Funkcja odpowaida za logowanie
    public boolean loginCheck(TextField emailTextField, PasswordField passwordFromUser) throws SQLException {
        ResultSet resultSet = null;
        String email = emailTextField.getText();
        String password = MD5.getMD5Password(passwordFromUser.getText());
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
            loadSettingsAboutUser(email);
            return true;
        }
        return false;
    }

    //Funkcja odpowiada za rejestracje użytkownika
    public boolean addNewUser(TextField emailTextField, PasswordField passwordFromUser
                              ) throws SQLException {
        String email = emailTextField.getText();
        String password = MD5.getMD5Password(passwordFromUser.getText());
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
                            addNewUserSettings(email);
                            return true;
                        } catch (SQLException e) {
                            System.out.println("ERROR:Bad SQL query");
                        }
                  return false;
        }

    //Funkcja sprawdza czy adres email nie dubluje się w bazie
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
                                              TextField humidityTextField, ComboBox<String> cloudinessFromUserComboBox, int IDFromListView,Label addMesureAlertLabel) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy    HH:mm");
        Date actualDate = getInstance().getTime();
        String userName = userNameToMeasures;
        String date = dateFormat.format(actualDate);
        String temperature = temperatureTextField.getText();
        if(temperature.length()==0){
            temperature="NULL";
        }
        String windSpeed = windTextField.getText();
        if(windSpeed.length()==0){
            windSpeed="NULL";
        }
        String humidity = humidityTextField.getText();
        if(humidity.length()==0){
            humidity="NULL";
        }
        String claudiness = cloudinessFromUserComboBox.getSelectionModel().getSelectedItem();

        String pressure = pressureTextField.getText();
        if(pressure.length()==0){
            pressure="NULL";
        }
        int ID = IDFromListView;
        String addMeasureFromUserQuerySQL =
                "INSERT INTO measuresfromusers (DATE,USERNAME,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,IDCITY) VALUES " +
                        "('" + date + "', '" + userName + "', " + temperature + ", " + windSpeed + ", " + humidity + ", '"
                        + claudiness + "', " + pressure + ", " + ID + ")";

        if(claudiness.length()==0) {
            claudiness = "NULL";

            addMeasureFromUserQuerySQL =
                    "INSERT INTO measuresfromusers (DATE,USERNAME,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,IDCITY) VALUES " +
                            "('" + date + "', '" + userName + "', " + temperature + ", " + windSpeed + ", " + humidity + ", "
                            + claudiness + ", " + pressure + ", " + ID + ")";
        }
        Statement stmt = null;
        if(checkDataBeforeAddMesure.veryficicationComplete(pressureTextField,temperatureTextField,windTextField,
                humidityTextField,addMesureAlertLabel)==true)  {
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
    //TE FUNKCJE ZWRACAJĄ POMIARY DLA WSZYSTKICH MIAST ICH OBRÓBKĄ ZAJMUJE SIE TablesForCityFactory
    public ArrayList<TemperatureFromUser> getTemperaturesFromUserList() throws SQLException {
        ArrayList<TemperatureFromUser> temperaturesFromUserArrayList = new ArrayList();
        String takeTemperatureMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE TEMP IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeTemperatureMeasuresQuerySQL);
         while (rs.next()){
                temperaturesFromUserArrayList.add(new TemperatureFromUser(rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(9)));
            }

        return temperaturesFromUserArrayList;
    }
    public ArrayList<WindSpeedFromUser> getWindSpeedFromUserList() throws SQLException {
        ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList = new ArrayList();
        String takeWindSpeedMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE WINDSPEED IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeWindSpeedMeasuresQuerySQL);
        while (rs.next()){
            windSpeedFromUserArrayList.add(new WindSpeedFromUser(rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(5),
                    rs.getInt(9)));
        }

        return windSpeedFromUserArrayList;
    }
public ArrayList<HumidityFromUser> getHumidityFromUserList() throws SQLException {
    ArrayList<HumidityFromUser> humidityFromUserArrayList = new ArrayList();
    String takeHumidityMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE HUMIDITY IS NOT NULL";
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(takeHumidityMeasuresQuerySQL);
    while (rs.next()){
        humidityFromUserArrayList.add(new HumidityFromUser(rs.getString(2),
                rs.getString(3),
                rs.getDouble(6),
                rs.getInt(9)));
    }
    return humidityFromUserArrayList;
}
    public ArrayList<PressureFromUser> getPressureFromUserList() throws SQLException {
        ArrayList<PressureFromUser> pressureFromUserArrayList = new ArrayList();
        String takePressureMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE PRESSURE IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takePressureMeasuresQuerySQL);
        while (rs.next()){
            pressureFromUserArrayList.add(new PressureFromUser(rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(8),
                    rs.getInt(9)));
        }
        return pressureFromUserArrayList;
    }
public ArrayList<ClaudinessFromUser> getClaudinessFromUserList() throws SQLException {
    ArrayList<ClaudinessFromUser> claudinessFromUserArrayList = new ArrayList();
    String takeClaudinessMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE CLOUDINESS IS NOT NULL";
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(takeClaudinessMeasuresQuerySQL);
    while (rs.next()){
        claudinessFromUserArrayList.add(new ClaudinessFromUser(rs.getString(2),
                rs.getString(3),
                rs.getString(7),
                rs.getInt(9)));
    }
    return claudinessFromUserArrayList;
}

    public boolean changeUserPassword(TextField emailToPasswordChange,PasswordField passwordFieldToChange,PasswordField confirmedPasswordFromUser,Label badPasswordLabel) throws SQLException {
        String password= MD5.getMD5Password(passwordFieldToChange.getText());
        String email=emailToPasswordChange.getText();
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
        return false;
    }
    //Funkcje dotyczace tabeli usersettings
    private void addNewUserSettings(String email){
    String addSettingsAboutNewUser="INSERT INTO usersettings (MINTEMP, MAXTEMP, MINWIND, MAXWIND, MINPRESSURE , MAXPRESSURE, EMAIL) VALUES (-50.0, 60.0, 0.0, 63.0, 870.0, 1086.0, '"+email+"')";
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        try {
            stmt.executeUpdate(addSettingsAboutNewUser);
        } catch (SQLException e) {
            System.out.println("ERROR:Bad SQL query");
        }
    }
    private void loadSettingsAboutUser(String email) throws SQLException {
        String loadSettingsAboutActualUser="SELECT * FROM usersettings where EMAIL='"+email+"'";
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        try {
            resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        } catch (SQLException e) {
            System.out.println("ERROR:Bad SQL query");
        }
        while (resultSet.next()) {
            userSettings.setMinTemperature(resultSet.getDouble(2));
            userSettings.setMaxTemperature(resultSet.getDouble(3));
            userSettings.setMinWindSpeed(resultSet.getDouble(4));
            userSettings.setMaxWindSpeed(resultSet.getDouble(5));
            userSettings.setMinPressure(resultSet.getDouble(6));
            userSettings.setMaxPressure(resultSet.getDouble(7));
        }
    }
    public void loadUserSettingsAboutAddMesure(TextField maxTemp,TextField minTemp, TextField minWind, TextField maxWind, TextField minPressure,TextField maxPressure){
        maxTemp.setText(String.valueOf(userSettings.getMaxTemperature()));
        minTemp.setText(String.valueOf(userSettings.getMinTemperature()));
        maxWind.setText(String.valueOf(userSettings.getMaxWindSpeed()));
        minWind.setText(String.valueOf(userSettings.getMinWindSpeed()));
        maxPressure.setText(String.valueOf(userSettings.getMaxPressure()));
        minPressure.setText(String.valueOf(userSettings.getMinPressure()));
    }
    public void changeUserSettings(TextField maxTemp,TextField minTemp, TextField minWind, TextField maxWind, TextField minPressure,TextField maxPressure,TextField email) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy na pewno chcesz zapisać ustawienia dla konta?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            double maxTempVal = Double.parseDouble(maxTemp.getText());
            double minTempVal = Double.parseDouble(minTemp.getText());
            double maxWindVal = Double.parseDouble(maxWind.getText());
            double minWindVal = Double.parseDouble(minWind.getText());
            double maxPressureVal = Double.parseDouble(maxPressure.getText());
            double minPressureVal = Double.parseDouble(minPressure.getText());
            String emailVal = email.getText();
            String changeUserSettingsQuerySQL = "UPDATE usersettings SET MINTEMP=" + minTempVal + ",MAXTEMP=" + maxTempVal + ",MAXWIND=" + maxWindVal + ",MINWIND=" + minWindVal + ",MINPRESSURE=" + minPressureVal + ",MAXPRESSURE=" + maxPressureVal + " WHERE EMAIL='" + emailVal + "'";
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("ERROR:No connection with Database");
            }
            try {
                stmt.executeUpdate(changeUserSettingsQuerySQL);
            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
            loadSettingsAboutUser(emailVal);
        }

    }
    public void loadDefaultSettingsForUser(TextField email) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy na pewno chcesz przywrócić ustawienia domyślne dla konta?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            String emailVal=email.getText();
            String loadDefaultUserSettingsQuerySQL = "UPDATE usersettings SET MINTEMP=-50.0, MAXTEMP=60.0, MAXWIND=63.0, MINWIND=0.0, MINPRESSURE=970.0, MAXPRESSURE=1086.0 WHERE EMAIL='"+ emailVal +"'";
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("ERROR:No connection with Database");
            }
            try {
                stmt.executeUpdate(loadDefaultUserSettingsQuerySQL);
            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
            loadSettingsAboutUser(emailVal);
        } else {

        }

    }
}

