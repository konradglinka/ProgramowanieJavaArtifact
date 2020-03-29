import MeasuresFromUsers.MesureFromUser;
import MeasuresFromUsers.TypeOfMeasure.*;
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
        String password = passwordFromUser.getText();
        String loginQuerySQL = "SELECT EMAIL,PASSWORD from users WHERE EMAIL = '" + email + "' AND PASSWORD ='" + password + "'";

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
            System.out.println("zalogowane");
            userNameToMeasures = email;
            return true;
        }

        return false;
    }

    //Funkcja odpowiada za rejestracje użytkownika
    public boolean addNewUser(TextField emailTextField, PasswordField passwordFromUser, PasswordField confirmedPasswordFromUser, Label registrationAlertLabel) throws SQLException {

        String email = emailTextField.getText();
        String password = passwordFromUser.getText();
        String confirmedPassword = confirmedPasswordFromUser.getText();
        if (isAccountAlreadyEmailInDataBase(email) == false) {


            if (password.equals(confirmedPassword)) {
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
            } else if (!password.equals(confirmedPassword)) {
                registrationAlertLabel.setVisible(true);
                registrationAlertLabel.setText("Hasła nie są jednakowe");
                return false;
            }
        }
        if (isAccountAlreadyEmailInDataBase(email) == true) {
            registrationAlertLabel.setVisible(true);
            registrationAlertLabel.setText("Podany adres e-mail jest juz w użyciu");
        }


        return false;
    }
    //Funkcja pomocnicza sprawdza czy adres email nie dubluje się w bazie
    private boolean isAccountAlreadyEmailInDataBase(String email) throws SQLException {
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
    public void addMeasuresFromUserToDataBase(TextField pressureTextField, TextField temperatureTextField, TextField windTextField, TextField humidityTextField, TextField cloudinessTextField, ListView<String> cityListListView) {
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

        String addUserQuerySQL =
                "INSERT INTO measuresfromusers (DATE,USERNAME,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,CITY) VALUES ('" + date + "', '" + userName + "', '" + temperature + "', '" + windSpeed + "', '" + humidity + "', '" + claudiness + "', '" + pressure + "', '" + city + "')";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:No connection with Database");
        }
        try {
            stmt.executeUpdate(addUserQuerySQL);

        } catch (SQLException e) {
            System.out.println("ERROR:Bad SQL query");
        }
    }

    //Metoda pobiera pomiary od użytkowników
    public void getMeasureFromUserListFromDataBase() throws SQLException {
        listOfMeasures.clear(); //Usuwamy poprzednie pomiary
        //Metoda zwraca liste rekordow z bazy danych
        String takeAllMeasures = "SELECT * FROM measuresfromusers";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(takeAllMeasures);
        while (rs.next()) {
            listOfMeasures.add(new MesureFromUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getDouble(8), rs.getString(9)));
        }

    }

    //TE FUNKCJE ZWRACAJĄ POMIARY DLA WSZYSTKICH MIAST ICH OBRÓBKĄ ZAJMUJE SIE TablesForCityFactory
    //Funkcja zwraca pomiary temperatury
    public ArrayList<TemperatureFromUser> getTemperaturesFromUserList() {
        ArrayList<TemperatureFromUser> temperaturesFromUserArrayList = new ArrayList();
        for (int i = 0; i < listOfMeasures.size(); i++) {
            if (listOfMeasures.get(i).getTemperature() != 0) {
                temperaturesFromUserArrayList.add(new TemperatureFromUser(listOfMeasures.get(i).getDate(), listOfMeasures.get(i).getUserName(), listOfMeasures.get(i).getTemperature(), listOfMeasures.get(i).getCity()));
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


}

