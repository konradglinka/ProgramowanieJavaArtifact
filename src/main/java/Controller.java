import DustyPlants.ActualDustyPlants;
import DustyPlants.DustyPlant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Controller {
    ActualDustyPlants actualDustyPlants=new ActualDustyPlants();
    JDBC jdbc=new JDBC();
    JDBCQuery jdbcQuery;







    @FXML
    TableView plantsTableView;
    @FXML
    VBox loginVBox;
    @FXML
    VBox registerVBox;
    @FXML
    StackPane registerAndLoginStackPane;
    @FXML
    TabPane mainViewTabPane;
    @FXML
    TextField emailTextField;

    @FXML
    TextField registrationEmailTextField;

    @FXML
    PasswordField passwordPasswordField;

    @FXML
    PasswordField registrationPasswordPasswordField;
    @FXML
    PasswordField registrationConfirmedPasswordPasswordField;
    @FXML
   Label badEmailOrPasswordLabel;
    @FXML
    Label registrationAlertLabel;
    @FXML
    ListView actualDustyPlantsListView;
    @FXML
    TableColumn<DustyPlant,String>styczenColumn;
    @FXML
    void initialize()  {

        actualDustyPlantsListView.getItems().addAll(actualDustyPlants.listOfActualDustyPlants());

       // jdbc.getDbConnection();
// jdbcQuery= new JDBCQuery(jdbc);

    }
    @FXML
    void switchOnRegisterButton(){
        loginVBox.setVisible(false);
        registerVBox.setVisible(true);
        registrationAlertLabel.setVisible(false);
    }
    @FXML
    void switchOnLoginButton(){
        loginVBox.setVisible(true);
        registerVBox.setVisible(false);
    }
    @FXML
    void loginButton() throws SQLException {
        if (jdbcQuery.loginCheck(emailTextField,passwordPasswordField) == true) {
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginCheck(emailTextField, passwordPasswordField) == false) {
            badEmailOrPasswordLabel.setVisible(true);
        }

    }

    @FXML
    void registerButton() throws SQLException {
       if (jdbcQuery.addNewUser(registrationEmailTextField,registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField,registrationAlertLabel)==true)
       {
           loginVBox.setVisible(true);
           registerVBox.setVisible(false);
       }
       else if (jdbcQuery.addNewUser(registrationEmailTextField,registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField,registrationAlertLabel)==false){

       }

    }

    @FXML
    void logoutButton(){
        registerAndLoginStackPane.setVisible(true);
        mainViewTabPane.setVisible(false);
    }













    // sluzy do uruchomienia bez xamppa
    @FXML
    void fakeLoginButton(){
        registerAndLoginStackPane.setVisible(false);
        mainViewTabPane.setVisible(true);
    }
}






