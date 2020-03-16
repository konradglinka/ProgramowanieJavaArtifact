import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCQuery {

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

}

