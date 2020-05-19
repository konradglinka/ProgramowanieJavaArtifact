import AnotherClasses.AddUserMeasureHelper;
import AnotherClasses.MD5;
import Objects.*;
import Repositories.FromDB.UserSettingsRepository;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static java.util.Calendar.getInstance;

public class NewJDBCQuery {
    private static Connection connection; //Polaczenie z baza danych
    private MD5 MD5 =new MD5(); //szyfrowanie hasła

    public NewJDBCQuery(JDBC jdbc) throws SQLException { //Laczymy sie z baza
        connection = jdbc.getConnection();
        // getMeasureFromUserListFromDataBase();
    }


    //REJESTRACJA
    public boolean addNewUser(TextField emailTextField, PasswordField passwordFromUser
    ) throws SQLException {
        String email = emailTextField.getText();
        String password = MD5.getMD5Password(passwordFromUser.getText());
        String addUserQuerySQL =
                "INSERT INTO users (EMAIL,PASSWORD) VALUES ('" + email+ "', '" + password + "')";

        Statement stmt =connection.createStatement();
        stmt.executeUpdate(addUserQuerySQL);
addNewUserSettings(getUserID(emailTextField));
            return true;
    }

    private void addNewUserSettings(int USERID) throws SQLException {
        String addSettingsAboutNewUser="INSERT INTO usersettings (MINTEMP, MAXTEMP, MINWIND, MAXWIND, MINPRESSURE , MAXPRESSURE, IDUSER) VALUES (-50.0, 60.0, 0.0, 63.0, 870.0, 1086.0,"+USERID+")";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(addSettingsAboutNewUser);
    }
    private int getUserID(TextField email) throws SQLException {
        String getUserIDQuerySQL="SELECT IDUSER FROM users WHERE EMAIL='"+email.getText()+"'";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(getUserIDQuerySQL);
        resultSet.next();
        return resultSet.getInt(1);
    }
    //FUNKCJA SPRAWDZA CZY PODANY PRZY REJESTRACJI ADRES NIE DUBLUJE SIĘ
    public boolean isAccountAlreadyEmailInDataBase(String email) throws SQLException {
        String findEmailInDataBase = "SELECT EMAIL from users WHERE EMAIL = '" + email + "'";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(findEmailInDataBase);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }
    //LOGOWANIE
    public boolean loginUser(UserSettingsRepository userSettingsRepository, TextField emailTextField, PasswordField passwordFromUser) throws SQLException {
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
            loadUserSettings(userSettingsRepository,emailTextField);
            return true;
        }


        return false;
    }
    private void checkUserSettings(TextField loginEmail) throws SQLException {
        String loadSettingsAboutActualUser="SELECT * FROM usersettings where IDUSER="+getUserID(loginEmail);
        Statement stmt = null;
        ResultSet resultSet = null;
        stmt = connection.createStatement();
        resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        if(!resultSet.next()){
            addNewUserSettings(getUserID(loginEmail));
        }
    }
    private void loadUserSettings(UserSettingsRepository userSettingsRepository, TextField loginEmail) throws SQLException {
        checkUserSettings(loginEmail);
        String loadSettingsAboutActualUser="SELECT * FROM usersettings where IDUSER="+getUserID(loginEmail);
        Statement stmt = null;
        ResultSet resultSet = null;
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
            while (resultSet.next()) {
            userSettingsRepository.setMinTemperature(resultSet.getDouble(3));
            userSettingsRepository.setMaxTemperature(resultSet.getDouble(4));
            userSettingsRepository.setMinWindSpeed(resultSet.getDouble(5));
            userSettingsRepository.setMaxWindSpeed(resultSet.getDouble(6));
            userSettingsRepository.setMinPressure(resultSet.getDouble(7));
            userSettingsRepository.setMaxPressure(resultSet.getDouble(8));
        }

    }
    //ZMIANA HASLA UŻYTKOWNIKA
    public boolean changeUserPassword(TextField emailToPasswordChange,PasswordField passwordFieldToChange,PasswordField confirmedPasswordFromUser,Label badPasswordLabel) throws SQLException {
        String password= MD5.getMD5Password(passwordFieldToChange.getText());
        String email=emailToPasswordChange.getText();
        String changeUserPasswordQuerySQL = "UPDATE users SET `PASSWORD`='" + password + "' WHERE email='" + email + "'";
        Statement stmt= connection.createStatement();
        stmt.executeUpdate(changeUserPasswordQuerySQL);
        return true;
    }

    //ZMIANA USTAWIEN
    public void changeUserSettings(UserSettingsRepository userSettingsRepository,TextField maxTemp,TextField minTemp, TextField minWind, TextField maxWind, TextField minPressure,TextField maxPressure,TextField email) throws SQLException {
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
            String changeUserSettingsQuerySQL = "UPDATE usersettings SET MINTEMP=" + minTempVal + ",MAXTEMP=" + maxTempVal + ",MAXWIND=" + maxWindVal + ",MINWIND=" + minWindVal + ",MINPRESSURE=" + minPressureVal + ",MAXPRESSURE=" + maxPressureVal + " WHERE IDUSER='" +getUserID(email) + "'";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(changeUserSettingsQuerySQL);
            loadUserSettings(userSettingsRepository,email);
        }
    }
    //PRZYWROCENIE USTAWIEN STARTOWYCH DLA USERA
    public void loadDefaultSettingsForUser(UserSettingsRepository userSettingsRepository,TextField email) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy na pewno chcesz przywrócić ustawienia domyślne dla konta?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            String loadDefaultUserSettingsQuerySQL = "UPDATE usersettings SET MINTEMP=-50.0, MAXTEMP=60.0, MAXWIND=63.0, MINWIND=0.0, MINPRESSURE=970.0, MAXPRESSURE=1086.0 WHERE IDUSER="+getUserID(email) +"";
            Statement stmt = connection.createStatement();
                stmt.executeUpdate(loadDefaultUserSettingsQuerySQL);
            loadUserSettings(userSettingsRepository,email);
        } else {
        }
    }
    //POBIERAMY DANE O ROSLINACH
    public ArrayList<DustyPlant> getdustyplantsTable() throws SQLException {
        ArrayList<DustyPlant>dustyPlants=new ArrayList<>();
        String getdustyplantsTableQuerySQL="SELECT * FROM dustyplants";
        Statement stmt =connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(getdustyplantsTableQuerySQL);
        while (resultSet.next()) {
            dustyPlants.add(new DustyPlant(resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6)));
        }
        return dustyPlants;
    }
    //POBIERAMY DANE O STACJACH OWM/USEROW/GIOS
    public ArrayList<Station> getstationsTable(String nameOfTable) throws SQLException {
        //nameOfTable zawiera nazwe tabeli z jakiej chcemy pobrac stacje
        //userstations - stacje do pomiarow od użytkowników
        //giosstations - stacje GIOS
        //owmstations -stacje OWM
        ArrayList<Station> stations =new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM "+nameOfTable;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        while (resultSet.next()) {
            stations.add(new Station(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4)));
        }
        return stations;
    }
    //POBIERAMY DANE O SENSORACH GIOS
    public ArrayList<GIOSSensor> getGiosSensorsFromDataBase() throws SQLException {
        ArrayList<GIOSSensor>giosSensors=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM giossensors";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        while (resultSet.next()) {
            giosSensors.add(new GIOSSensor(resultSet.getInt(2),resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4)));
        }
        return giosSensors;
    }
    //POBIERAMY DANE TŁUMACZA ZACHMURZENIA
    //PL
    public ArrayList<String> getCloudinessTranslatorTablePLNames() throws SQLException {
        ArrayList<String>PLNames=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT PL FROM cloudinesstranslator";
        Statement stmt = connection.createStatement();
        ResultSet resultSet =  resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        while (resultSet.next()) {
            PLNames.add(resultSet.getString(1));
        }
        return PLNames;
    }
    //ENG
    public ArrayList<String> getCloudinessTranslatorTableENGNames() throws SQLException {
        ArrayList<String>ENGNames=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT ENG FROM cloudinesstranslator";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(loadSettingsAboutActualUser);
        while (resultSet.next()) {
            ENGNames.add(resultSet.getString(1));
        }
        return ENGNames;
    }
    //DODANIE POMIARU PRZEZ UŻYTKOWNIKA
    public void addMeasuresFromUserToDataBase(AddUserMeasureHelper addUserMeasureHelper,TextField loginEmail,TextField pressureTextField, TextField temperatureTextField, TextField windTextField,
                                              TextField humidityTextField, ComboBox<String> cloudinessFromUserComboBox, int IDUserStation,Label addMesureAlertLabel) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date actualDate = getInstance().getTime();
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
        if(claudiness.length()==0) {
            claudiness = "NULL";
        }
        String pressure = pressureTextField.getText();
        if(pressure.length()==0){
            pressure="NULL";
        }
        int ID = IDUserStation;
        String addMeasureFromUserQuerySQL =
                "INSERT INTO measuresfromusers (DATE,IDUSER,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,IDUSERSTATION) VALUES " +
                        "('" + date + "', '" + getUserID(loginEmail) + "', " + temperature + ", " + windSpeed + ", " + humidity +
                        ", '" + claudiness + "', " + pressure + ", " + ID + ")";
        Statement stmt = connection.createStatement();
        if(addUserMeasureHelper.veryficicationComplete(pressureTextField,temperatureTextField,windTextField,
                humidityTextField,addMesureAlertLabel)==true)  {
                stmt.executeUpdate(addMeasureFromUserQuerySQL);
        }
    }
    //POBIERA DANE O TEMPERATURZE Z BAZY
    public ArrayList<TemperatureFromUser> getTemperaturesFromUserList() throws SQLException {
        ArrayList<TemperatureFromUser> temperaturesFromUserArrayList = new ArrayList();
        String takeTemperatureMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE TEMP IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeTemperatureMeasuresQuerySQL);
        while (rs.next()){
            temperaturesFromUserArrayList.add(new TemperatureFromUser(rs.getString(4),
                    getEmailUser(rs.getInt(2)),
                    rs.getDouble(5),
                    rs.getInt(3)));
        }
        return temperaturesFromUserArrayList;
    }
    private String getEmailUser(int ID) throws SQLException {
        String takeTemperatureMeasuresQuerySQL = "SELECT EMAIL FROM users WHERE IDUSER="+ID;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeTemperatureMeasuresQuerySQL);
        rs.next();
        return rs.getString(1);
    }
    //POBIERA DANE O PREKOSCI WIATRU Z BAZY
    public ArrayList<WindSpeedFromUser> getWindSpeedFromUserList() throws SQLException {
        ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList = new ArrayList();
        String takeWindSpeedMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE WINDSPEED IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeWindSpeedMeasuresQuerySQL);
        while (rs.next()){
            windSpeedFromUserArrayList.add(new WindSpeedFromUser(rs.getString(4),
                    getEmailUser(rs.getInt(2)),
                    rs.getDouble(6),
                    rs.getInt(3)));
        }
        return windSpeedFromUserArrayList;
    }
    //POBIERA DANE O WILGOTNOSCI Z BAZY
    public ArrayList<HumidityFromUser> getHumidityFromUserList() throws SQLException {
        ArrayList<HumidityFromUser> humidityFromUserArrayList = new ArrayList();
        String takeHumidityMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE HUMIDITY IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeHumidityMeasuresQuerySQL);
        while (rs.next()){
            humidityFromUserArrayList.add(new HumidityFromUser(rs.getString(4),
                    getEmailUser(rs.getInt(2)),
                    rs.getDouble(7),
                    rs.getInt(3)));
        }
        return humidityFromUserArrayList;
    }
    //POBIERA DANE O CISNIENIU Z BAZY
    public ArrayList<PressureFromUser> getPressureFromUserList() throws SQLException {
        ArrayList<PressureFromUser> pressureFromUserArrayList = new ArrayList();
        String takePressureMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE PRESSURE IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takePressureMeasuresQuerySQL);
        while (rs.next()){
            pressureFromUserArrayList.add(new PressureFromUser(rs.getString(4),
                    getEmailUser(rs.getInt(2)),
                    rs.getDouble(9),
                    rs.getInt(3)));
        }
        return pressureFromUserArrayList;
    }
    //POBIERA DANE O ZACHMURZENIU Z BAZY
    public ArrayList<ClaudinessFromUser> getCloudinessFromUserList() throws SQLException {
        ArrayList<ClaudinessFromUser> claudinessFromUserArrayList = new ArrayList();
        String takeClaudinessMeasuresQuerySQL = "SELECT * FROM measuresfromusers WHERE CLOUDINESS IS NOT NULL";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeClaudinessMeasuresQuerySQL);
        while (rs.next()){
            claudinessFromUserArrayList.add(new ClaudinessFromUser(rs.getString(4),
                    getEmailUser(rs.getInt(2)),
                    rs.getString(8),
                    rs.getInt(3)));
        }
        return claudinessFromUserArrayList;
    }

}
