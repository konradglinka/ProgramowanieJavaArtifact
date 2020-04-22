import MeasuresFromUsers.MesureFromUser;
import MeasuresFromUsers.TypeOfMeasure.*;
import MeasuresFromUsers.CheckDataBeforeAddMesure;
import RegisterAndLoginActions.VerificateDataFromUser;
import javafx.scene.control.*;
import org.jetbrains.annotations.Nullable;

import javax.crypto.NullCipher;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Calendar.getInstance;

public class JDBCQuery { //Klasa zawiera metody współpracujące z bazą danych
    CheckDataBeforeAddMesure checkDataBeforeAddMesure = new CheckDataBeforeAddMesure();
    VerificateDataFromUser verificateDataFromUser = new VerificateDataFromUser();
   MD5 MD5 =new MD5();
    private String userNameToMeasures = "USER"; //Nazwa użytkownika do testów to USER w przypadku braku podania email
    ArrayList<MesureFromUser> listOfMeasures = new ArrayList<>(); //Lista wszystkich pomiarów od użytkownika
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
                                              TextField humidityTextField, ComboBox<String> cloudinessFromUserComboBox, int IDFromListView) {
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
                humidityTextField, cloudinessFromUserComboBox)==true)  {
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
   /* public void getMeasureFromUserListFromDataBase() throws SQLException {
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
*/
    //TE FUNKCJE ZWRACAJĄ POMIARY DLA WSZYSTKICH MIAST ICH OBRÓBKĄ ZAJMUJE SIE TablesForCityFactory
    //Funkcja zwraca pomiary temperatury
   /* public ArrayList<TemperatureFromUser> getTemperaturesFromUserList() {
        ArrayList<TemperatureFromUser> temperaturesFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getTemperature()!=999999999) {
                temperaturesFromUserArrayList.add(new TemperatureFromUser(listOfMeasures.get(i).getDate(),
                        listOfMeasures.get(i).getUserName(),
                        listOfMeasures.get(i).getTemperature(),
                        listOfMeasures.get(i).getCity()));
            }
        }
        return temperaturesFromUserArrayList;
    }*/
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
    //Funkcja zwraca pomiaru predkości wiatru
    /*public ArrayList<WindSpeedFromUser> getWindSpeedFromUserList() {
        ArrayList<WindSpeedFromUser> windSpeedFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getWindSpeed() != 999999999) {
                windSpeedFromUserArrayList.add(new WindSpeedFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getWindSpeed(), listOfMeasures.get(i).getCity()));
            }
        }
        return windSpeedFromUserArrayList;
    }*/
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
//Funkcja zwraca pomiary wilgotności
    /*public ArrayList<HumidityFromUser> getHumidityFromUserList() {
        ArrayList<HumidityFromUser> humidityFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getHumidity() != 999999999) {
                humidityFromUserArrayList.add(new HumidityFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getHumidity(), listOfMeasures.get(i).getCity()));
            }
        }
        return humidityFromUserArrayList;
    } */
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
//Funckja zwraca pomiary ciśnienia powietrza

    /*public ArrayList<PressureFromUser> getPressureFromUserList() {
        ArrayList<PressureFromUser> pressureFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getPressure() != 999999999) {
                pressureFromUserArrayList.add(new PressureFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getPressure(), listOfMeasures.get(i).getCity()));
            }
        }
        return pressureFromUserArrayList;
    }
    */
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
//Funkcja zwraca pomiary zachmurzenia
    /*
    public ArrayList<ClaudinessFromUser> getClaudinessFromUserList() {
        ArrayList<ClaudinessFromUser> claudinessFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (!listOfMeasures.get(i).getClaudiness().equals("")) {
                claudinessFromUserArrayList.add(new ClaudinessFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getClaudiness(), listOfMeasures.get(i).getCity()));
            }
        }
        return claudinessFromUserArrayList;
    }
*/
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


}

