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
import java.time.LocalDate;
import java.util.Date;

import static java.util.Calendar.getInstance;

public class JDBCQuery {
    private String userNameToMeasures="USER";
    private static Connection connection; //Polaczenie z baza danych

    public JDBCQuery(JDBC jdbc) { //Laczymy sie z baza
        connection = jdbc.getConnection();

    }

    public boolean loginCheck (TextField emailTextField, PasswordField passwordFromUser) throws SQLException {
        ResultSet resultSet = null;
       String email = emailTextField.getText();
       String password = passwordFromUser.getText();
       String loginQuerySQL = "SELECT EMAIL,PASSWORD from users WHERE EMAIL = '"+email+"' AND PASSWORD ='"+password+"'";

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
            userNameToMeasures=email;
            return true;
        }
        return false;
    }
    public boolean addNewUser (TextField emailTextField, PasswordField passwordFromUser, PasswordField confirmedPasswordFromUser, Label registrationAlertLabel) throws SQLException {

        String email = emailTextField.getText();
        String password = passwordFromUser.getText();
String confirmedPassword=confirmedPasswordFromUser.getText();
if(isAccountAlreadyEmailInDataBase(email)==false) {


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
if(isAccountAlreadyEmailInDataBase(email)==true){
    registrationAlertLabel.setVisible(true);
    registrationAlertLabel.setText("Podany adres e-mail jest juz w użyciu");
}


        return false;
    }
    boolean isAccountAlreadyEmailInDataBase(String email) throws SQLException {
        ResultSet resultSet = null;


        String findEmailInDataBase = "SELECT EMAIL from users WHERE EMAIL = '"+email+"'";

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


public void addMeasuresFromUserToDataBase(TextField pressureTextField, TextField temperatureTextField, TextField windTextField, TextField humidityTextField, TextField cloudinessTextField, ListView<String>cityListListView){
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date actualDate = getInstance().getTime();
    String userName=userNameToMeasures;
    String date= dateFormat.format(actualDate);
    String temperature=temperatureTextField.getText();
    String windSpeed=windTextField.getText();
    String humidity=humidityTextField.getText();
    String claudiness=cloudinessTextField.getText();
    String pressure=pressureTextField.getText();
    String city=cityListListView.getSelectionModel().getSelectedItem();



    String addUserQuerySQL =
            "INSERT INTO measuresfromusers (DATE,USERNAME,TEMP,WINDSPEED,HUMIDITY,CLOUDINESS,PRESSURE,CITY) VALUES ('" + date + "', '" + userName + "', '"+temperature+"', '"+windSpeed+"', '"+humidity+"', '"+claudiness+"', '"+pressure+"', '"+city+"')";

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
    public void takeMeasuresFromUserFromDataBase (TextField emailTextField, PasswordField passwordFromUser) throws SQLException {
        ResultSet resultSet = null;
        String email = emailTextField.getText();
        String password = passwordFromUser.getText();
        String loginQuerySQL = "SELECT ,PASSWORD from users WHERE EMAIL = '"+email+"' AND PASSWORD ='"+password+"'";

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

        }

    }



}

