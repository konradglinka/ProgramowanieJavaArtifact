/*
import AnotherClasses.MD5;
import Objects.DustyPlant;
import Objects.GIOSSensor;
import AnotherClasses.AddUserMeasureHelper;
import Repositories.FromDB.UserSettingsRepository;
import Objects.*;
import AnotherClasses.RegisterHelper;
import javafx.scene.control.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static java.util.Calendar.getInstance;

public class JDBCQuery { //Klasa zawiera metody współpracujące z bazą danych




    private AnotherClasses.MD5 MD5 =new MD5();
    private String userNameToMeasures = "USER"; //Nazwa użytkownika do testów to USER w przypadku braku podania email
    private static Connection connection; //Polaczenie z baza danych



    public JDBCQuery(JDBC jdbc) throws SQLException { //Laczymy sie z baza
        connection = jdbc.getConnection();
       // getMeasureFromUserListFromDataBase();
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
                            addNewUserSettings(email);
                            stmt = connection.createStatement();
                        } catch (SQLException e) {
                            System.out.println("ERROR:No connection with Database");
                        }
                        try {
                            stmt.executeUpdate(addUserQuerySQL);
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


    //TE FUNKCJE ZWRACAJĄ POMIARY DLA WSZYSTKICH MIAST ICH OBRÓBKĄ ZAJMUJE SIE UserMeasuresRepository

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
    String addSettingsAboutNewUser="INSERT INTO usersettings (MINTEMP, MAXTEMP, MINWIND, MAXWIND, MINPRESSURE , MAXPRESSURE, USERID) VALUES (-50.0, 60.0, 0.0, 63.0, 870.0, 1086.0, '"+email+"')";
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


    public ArrayList<String> getCloudinessTranslatorPLNamesFromDataBase() throws SQLException {
        ArrayList<String>PLNames=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT PL FROM cloudinesstranslator";
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
          PLNames.add(resultSet.getString(1));
        }
        return PLNames;
    }
    public ArrayList<String> getCloudinessTranslatorENGNamesFromDataBase() throws SQLException {
        ArrayList<String>ENGNames=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT ENG FROM cloudinesstranslator";
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
            ENGNames.add(resultSet.getString(1));
        }
        return ENGNames;
    }
/*
    //GIOS DATABASES
    public void addValuesToGiosStationsTable() throws IOException {
        ListOfGIOSCitiesFactory listOfGIOSCitiesFactory=new ListOfGIOSCitiesFactory();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        for(int i=0;i<listOfGIOSCitiesFactory.getStationsArrayList().size();i++) {
            try {
                String addGiosStationQuerySQL="INSERT INTO giosstations (IDSTATION,STATIONNAME,LON,LAT) VALUES ("+listOfGIOSCitiesFactory.getStationsArrayList().get(i).getId()+",'"+listOfGIOSCitiesFactory.getStationsArrayList().get(i).getName()+"',"+listOfGIOSCitiesFactory.getStationsArrayList().get(i).getLon()+","+listOfGIOSCitiesFactory.getStationsArrayList().get(i).getLat()+")";

                stmt.executeUpdate(addGiosStationQuerySQL);
            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
        }
    }
    public void addValuesToGiosSensorsTable() throws IOException {
        ListOfGIOSSensorsFactory listOfGIOSSensorsFactory=new ListOfGIOSSensorsFactory();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        for(int i=0;i<listOfGIOSSensorsFactory.getSensorsArrayList().size();i++) {
            try {
                String addGiosSensorsQuerySQL="INSERT INTO giossensors (IDSTATION,IDSENSOR,NAME,SHORTNAME) VALUES ("+listOfGIOSSensorsFactory.getSensorsArrayList().get(i).getIDStation()+","+listOfGIOSSensorsFactory.getSensorsArrayList().get(i).getIDSensor()+",'"+listOfGIOSSensorsFactory.getSensorsArrayList().get(i).getNameOfSensor()+"','"+listOfGIOSSensorsFactory.getSensorsArrayList().get(i).getShortNameOfSensor()+"')";
                stmt.executeUpdate(addGiosSensorsQuerySQL);
            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
        }
    }


    public ArrayList<Station> getGiosStatonsFromDataBase() throws SQLException {
        ArrayList<Station>giosStations=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM giosstations";
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
            giosStations.add(new Station(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4)));
        }
        return giosStations;
    }
    public ArrayList<GIOSSensor> getGiosSensorsFromDataBase() throws SQLException {
        ArrayList<GIOSSensor>giosSensors=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM giossensors";
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
            giosSensors.add(new GIOSSensor(resultSet.getInt(2),resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4)));
        }
        return giosSensors;
    }
    public ArrayList<DustyPlant> getDustyPlantsFromDataBase() throws SQLException {
        ArrayList<DustyPlant>dustyPlants=new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM dustyplants";
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
            dustyPlants.add(new DustyPlant(resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6)));
        }
        return dustyPlants;
    }
    /*public void addValuesToOWMStationsTable() throws IOException {
        OWMStationsRepository OWMStationsRepository=new OWMStationsRepository();
        ArrayList<Station>list=OWMStationsRepository.getStations();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        for(int i=0;i<list.size();i++) {
            try {
                String addGiosSensorsQuerySQL="INSERT INTO owmstations (IDSTATION,NAME,LON,LAT) VALUES ("+list.get(i).getId()+",'"+list.get(i).getName()+"',"+list.get(i).getLon()+","+list.get(i).getLat()+")";
                stmt.executeUpdate(addGiosSensorsQuerySQL);
            } catch (SQLException e) {
                System.out.println("ERROR:Bad SQL query");
            }
        }
    }

    public ArrayList<Station> getOWMStationsFromDataBase() throws SQLException {
        ArrayList<Station> stations =new ArrayList<>();
        String loadSettingsAboutActualUser="SELECT * FROM owmstations";
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
            stations.add(new Station(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4)));
        }
        return stations;
    }
}
*/

