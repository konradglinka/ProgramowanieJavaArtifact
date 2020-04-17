import DustyPlants.ActualDustyPlants;
import EmailActions.EmailToRegister;
import EmailActions.EmailToResetPassword;
import MeasuresFromUsers.MeasuresPLNamesFactory;
import MeasuresFromUsers.TablesForCityFactory;
import MeasuresFromUsers.TypeOfMeasure.*;
import OWM.WeatherMeasureOWM;
import OWM.WeatherMeasuresFactory;
import RegisterAndLoginActions.VerificateDataFromUser;
import WeatherInCities.ListOfCitiesFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


public class Controller {

    String resetPasswordCode;
    String registerCode;
    VerificateDataFromUser verificateDataFromUser = new VerificateDataFromUser();
    ActualDustyPlants actualDustyPlants = new ActualDustyPlants(); //Pobieramy listę aktualnie pylących roślin
    WeatherMeasuresFactory weatherMeasuresFactory;

    TablesForCityFactory tablesForCityFactory = new TablesForCityFactory(); //Do tworzenia tabel z pomiarami dla wybranych miast
    MeasuresPLNamesFactory measuresPLNamesFactory = new MeasuresPLNamesFactory();//Pobieramy listę dostępnych pomiarów
    ListOfCitiesFactory listOfCitiesFactory = new ListOfCitiesFactory(); //Pobieramy liste dostępnych miast

    JDBC jdbc = new JDBC(); //Do połączenia z baza
    JDBCQuery jdbcQuery; //Do wykonywania zapytań do bazy

    //ELEMENTY LOGOWANIA I REJESTRACJI
    @FXML
    StackPane registerAndLoginStackPane;
    //LOGOWANIE
    @FXML
    VBox loginVBox;
    @FXML
    TextField loginEmailTextField;
    @FXML
    PasswordField passwordPasswordField;
    //REJESTRACJA
    @FXML
    VBox registerVBox;
    @FXML
    TextField registrationEmailTextField;
    @FXML
    PasswordField registrationPasswordPasswordField;
    @FXML
    PasswordField registrationConfirmedPasswordPasswordField;
    @FXML
    Label badEmailOrPasswordLabel;
    @FXML
    Label registrationAlertLabel;
    //ELEMENTY GŁÓWNEGO OKNA APLIKACJI
    @FXML
    TabPane mainViewTabPane;
    //ELEMENTY OKNA Z ROŚLINAMI PYLĄCYMI
    @FXML
    ListView actualDustyPlantsListView;
    //ELEMENTY DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> cityToAddMeasureListView;
    @FXML
    TextField temperatureTextField;
    @FXML
    TextField humidityTextField;
    @FXML
    TextField windSpeedTextField;
    @FXML
    TextField claudinessTextField;
    @FXML
    TextField pressureTextField;
    //ELEMENTY POBRANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> cityToTakeMaeasureFromUserListView;
    @FXML
    ComboBox<String> measuresFromUserComboBox;  //wybór rodzaju pomiaru
    //TEMPERATURA POWIETRZA
    @FXML
    TableView<TemperatureFromUser> temperatureTableView;
    @FXML
    TableColumn<TemperatureFromUser, String> dateTempUser;
    @FXML
    TableColumn<TemperatureFromUser, String> userNameTemp;
    @FXML
    TableColumn<TemperatureFromUser, Double> temperature;
    //PREDKOŚĆ WIATRU
    @FXML
    TableView<WindSpeedFromUser> windSpeedTableView;
    @FXML
    TableColumn<WindSpeedFromUser, String> dateWindSpeedUser;
    @FXML
    TableColumn<WindSpeedFromUser, String> userNameWindSpeed;
    @FXML
    TableColumn<WindSpeedFromUser, Double> windSpeed;
    //WILGOTNOŚĆ POWIETRZA
    @FXML
    TableView<HumidityFromUser> humidityTableView;
    @FXML
    TableColumn<HumidityFromUser, String> dateHumidityUser;
    @FXML
    TableColumn<HumidityFromUser, String> userNameHumidity;
    @FXML
    TableColumn<HumidityFromUser, Double> humidity;
    //CIŚNIENIE POWIETRZA
    @FXML
    TableView<PressureFromUser> pressureTableView;
    @FXML
    TableColumn<PressureFromUser, String> datePressureUser;
    @FXML
    TableColumn<PressureFromUser, String> userNamePressure;
    @FXML
    TableColumn<PressureFromUser, Double> pressure;
    //ZACHMURZENIE
    @FXML
    TableView<ClaudinessFromUser> claudinessTableView;
    @FXML
    TableColumn<ClaudinessFromUser, String> dateClaudinessUser;
    @FXML
    TableColumn<ClaudinessFromUser, String> userNameClaudiness;
    @FXML
    TableColumn<ClaudinessFromUser, String> claudiness;



    //OWM
    @FXML
    ListView<String> cityToTakeMaeasureFromOWMListView;
    @FXML
    TextField nameOfCityToFindTextField;
    @FXML
    TableView<WeatherMeasureOWM>measuresFromOWMTableView;
    @FXML
    TableColumn<WeatherMeasureOWM,Double>tempColumn;
    @FXML
    TableColumn<WeatherMeasureOWM,Double>pressureColumn;
    @FXML
    TableColumn<WeatherMeasureOWM,Double>windColumn;
    @FXML
    TableColumn<WeatherMeasureOWM,Double>humidityColumn;
    @FXML
    TableColumn<WeatherMeasureOWM,String>claudinessColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, String>dateOWMColumn;



    //RESETOWANIE HASŁA
    @FXML
    VBox changePassword1VBox;
    @FXML
    VBox changePassword2VBox;
    @FXML
    VBox changePassword3VBox;
    @FXML
    TextField emailToResetPasswordTextField;
    @FXML
    TextField resetCodeTextField;
    @FXML
    PasswordField resetPasswordPasswordField;
    @FXML
    PasswordField conifrmedResetPasswordPasswordField;
    @FXML
    Label badPasswordLabel;
    @FXML
    VBox register2VBox;
    @FXML
    TextField registrationCodeTextField;
    @FXML
    Label badEmailResetPasswordLabel;
    @FXML
    Label badCodeResetPasswordLabel;
    @FXML
    Label registrationCodeAlertLabel;




    @FXML
    TextField nameOfCityToFindMesureFromUserTextField;

    @FXML
    TextField nameOfCityToAddMesureFromUserTextField;



    //ADRIAN ŻAK settingsMaxTemperatureTextField
    // Ma byc ich 8 max oraz min do temperature pressure , wind speed oraz humidity bez caludines bo to wyraza text.
    @FXML
    TextField setingsMaxTemperatureTextField;


    public Controller() throws IOException {
    }

    @FXML
    void initialize() {
        startConectionWithDataBase(); //Łączymy się z bazą w celu uwierzytelnienia i dalszej pracy aplikacji
        measuresFromUserComboBox.getItems().addAll(measuresPLNamesFactory.getNamesOfMeasuresArraylist()); //Lista dostępnych pomiarów do wyboru
        actualDustyPlantsListView.getItems().addAll(actualDustyPlants.listOfActualDustyPlants()); //Lista aktualnie pylących roślin
        cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList()); //Lista miast do dodania pomiaru
        cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList()); //Lista miast do pobrania pomiaru
        cityToTakeMaeasureFromOWMListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList());
        measuresFromUserComboBox.getSelectionModel().select(0); //Wybieramy 1 z wartości combobox by nie byl pusty
        cityToAddMeasureListView.getSelectionModel().selectFirst();
        cityToTakeMaeasureFromUserListView.getSelectionModel().selectFirst();
        cityToTakeMaeasureFromOWMListView.getSelectionModel().selectFirst();
    }
    private void startConectionWithDataBase() { //Połączenie aplikacji z bazą danych
        jdbc.getDbConnection();
        try {
            jdbcQuery = new JDBCQuery(jdbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    @FXML
    void switchOnRegisterButton() { //Funkcja przełącza na okienko do rejestracji
        loginVBox.setVisible(false);
        registerVBox.setVisible(true);
        registrationAlertLabel.setVisible(false);
    }



    @FXML
    void loginButton() throws SQLException { //Funkcja zajmuje się uwierzytelnianiem i przełącza na główny ekran aplikacji
        if (jdbcQuery.loginCheck(loginEmailTextField, passwordPasswordField) == true) {
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginCheck(loginEmailTextField, passwordPasswordField) == false) {
            badEmailOrPasswordLabel.setVisible(true);
        }
    }
    @FXML
    void fakeLoginButton() { //Funkcja przełącza naekran główny bez uwierzytelniania (TYLKO DO TESTOWANIA APLIKACJI, ZASTĘPUJE loginButton)
        registerAndLoginStackPane.setVisible(false);
        mainViewTabPane.setVisible(true);
    }
    @FXML
    void registerButton() throws SQLException { //Funkcja dodaje nowego użytkownika do bazy i przechodzi do logowania

       if(registerCode.equals(registrationCodeTextField.getText())) {
           if (jdbcQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField) == true) {
               loginVBox.setVisible(true);
               register2VBox.setVisible(false);
           }
       }
       else
       {
           registrationCodeAlertLabel.setVisible(true);
           registrationCodeAlertLabel.setText("Nie prawidłowy kod");
       }
}

    @FXML
    void logoutButton() { //Funkcja wylogowywuje z aplikacji i przełącza do logowania
        registerAndLoginStackPane.setVisible(true);
        mainViewTabPane.setVisible(false);
    }
    //FUNKCJE DOTYCZĄCE DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    void addMeasureFromUserButton() {
        jdbcQuery.addMeasuresFromUserToDataBase(pressureTextField, temperatureTextField, windSpeedTextField, humidityTextField, claudinessTextField, cityToAddMeasureListView);
    }
    //FUNKCJE DOTYCZĄCE POBRANIA POMIARU PRZEZ UŻYTKOWNIKA

    private void turnOffAllMeasuresFromUser() { //Funkcja pomocnicza wyłącza wszystkie widoczne tabele z pomiarami
        claudinessTableView.setVisible(false);
        temperatureTableView.setVisible(false);
        pressureTableView.setVisible(false);
        windSpeedTableView.setVisible(false);
        humidityTableView.setVisible(false);
    }

    private void clearAllTablesWithMeasuresFromUser() { //Funkcja pomocnicza czyści wszystkie tabele z pomiarami
        temperatureTableView.getItems().removeAll();
        pressureTableView.getItems().removeAll();
        windSpeedTableView.getItems().removeAll();
        humidityTableView.getItems().removeAll();
        claudinessTableView.getItems().removeAll();

    }

    @FXML
    void showMeasuresFromUserButton() { //Funkcja odpowiadająca za wypełnienie tabeli pomiarami po kliknieciu przycisku
        turnOffAllMeasuresFromUser(); //Wyłączamy poprzedni widok
        clearAllTablesWithMeasuresFromUser(); //Czyścimy poprzednie tabele
        try {
            jdbcQuery.getMeasureFromUserListFromDataBase(); //Pobieramy pomiary z bazy za każdym razem na nowo by dane były aktualne
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {
            temperatureTableView.setVisible(true);
            ObservableList<TemperatureFromUser> listOfTemperatureResults = FXCollections.observableArrayList(tablesForCityFactory.showTemperaturesFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getTemperaturesFromUserList()));
            dateTempUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameTemp.setCellValueFactory(new PropertyValueFactory<>("userName"));
            temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            temperatureTableView.setItems(listOfTemperatureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Wilgotność powietrza")) {
            humidityTableView.setVisible(true);
            ObservableList<HumidityFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showHumidityFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getHumidityFromUserList()));
            dateHumidityUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameHumidity.setCellValueFactory(new PropertyValueFactory<>("userName"));
            humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            humidityTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Prędkość wiatru")) {
            windSpeedTableView.setVisible(true);
            ObservableList<WindSpeedFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showWindSpeedFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getWindSpeedFromUserList()));
            dateWindSpeedUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameWindSpeed.setCellValueFactory(new PropertyValueFactory<>("userName"));
            windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
            windSpeedTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Ciśnienie")) {
            pressureTableView.setVisible(true);
            ObservableList<PressureFromUser> listOfPressureResults = FXCollections.observableArrayList(tablesForCityFactory.showPressureFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getPressureFromUserList()));
            datePressureUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNamePressure.setCellValueFactory(new PropertyValueFactory<>("userName"));
            pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            pressureTableView.setItems(listOfPressureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Zachmurzenie")) {
            claudinessTableView.setVisible(true);
            ObservableList<ClaudinessFromUser> listOfClaudinessResults = FXCollections.observableArrayList(tablesForCityFactory.showClaudinessFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getClaudinessFromUserList()));
            dateClaudinessUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameClaudiness.setCellValueFactory(new PropertyValueFactory<>("userName"));
            claudiness.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            claudinessTableView.setItems(listOfClaudinessResults);
        }
    }

    @FXML
    void findCityToTakeOWMDataButton() throws FileNotFoundException, APIException, ParseException {
        String input = nameOfCityToFindTextField.getText().toLowerCase();
String firstInput=input.substring(0,1);
String finalInput =firstInput.toUpperCase()+input.substring(1); //Ostateczna nazwa miasta


        if(listOfCitiesFactory.getCitiesArrayList().contains(finalInput)) {
            cityToTakeMaeasureFromOWMListView.getSelectionModel().select(finalInput);
            nameOfCityToFindTextField.setStyle("-fx-control-inner-background: green;");
            weatherMeasuresFactory= new WeatherMeasuresFactory(cityToTakeMaeasureFromOWMListView.getSelectionModel().getSelectedItem(),39);
            ObservableList<WeatherMeasureOWM> listOfWeatherMeasures = FXCollections.observableArrayList(weatherMeasuresFactory.getWeatherMeasuresListOWM());
            tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp") );
            windColumn.setCellValueFactory(new PropertyValueFactory<>("wind"));
            humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            claudinessColumn.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            dateOWMColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfMeasure"));
            measuresFromOWMTableView.setItems(listOfWeatherMeasures);
        }
        else {
            nameOfCityToFindTextField.setStyle("-fx-control-inner-background: red;");
        }


}
@FXML
void findCityToTakeMeasureFromUsersButton(){
    String input = nameOfCityToFindMesureFromUserTextField.getText().toLowerCase();
    String firstInput=input.substring(0,1);
    String finalInput =firstInput.toUpperCase()+input.substring(1); //Ostateczna nazwa miasta
    if(listOfCitiesFactory.getCitiesArrayList().contains(finalInput)) {
        cityToTakeMaeasureFromUserListView.getSelectionModel().select(finalInput);
        nameOfCityToFindMesureFromUserTextField.setStyle("-fx-control-inner-background: green;");
    }
    else {
        nameOfCityToFindMesureFromUserTextField.setStyle("-fx-control-inner-background: red;");
    }
}
    @FXML
    void findCityToAddMeasureFromUsersButton(){
        String input = nameOfCityToAddMesureFromUserTextField.getText().toLowerCase();
        String firstInput=input.substring(0,1);
        String finalInput =firstInput.toUpperCase()+input.substring(1); //Ostateczna nazwa miasta
        if(listOfCitiesFactory.getCitiesArrayList().contains(finalInput)) {
            cityToAddMeasureListView.getSelectionModel().select(finalInput);
            nameOfCityToAddMesureFromUserTextField.setStyle("-fx-control-inner-background: green;");
        }
        else {
            nameOfCityToAddMesureFromUserTextField.setStyle("-fx-control-inner-background: red;");
        }
    }
@FXML
void onClickOWMCityListView() throws FileNotFoundException, APIException, ParseException {
    weatherMeasuresFactory= new WeatherMeasuresFactory(cityToTakeMaeasureFromOWMListView.getSelectionModel().getSelectedItem(),39);
    ObservableList<WeatherMeasureOWM> listOfWeatherMeasures = FXCollections.observableArrayList(weatherMeasuresFactory.getWeatherMeasuresListOWM());
    tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp") );
    windColumn.setCellValueFactory(new PropertyValueFactory<>("wind"));
    humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
    pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
    claudinessColumn.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
    dateOWMColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfMeasure"));
    measuresFromOWMTableView.setItems(listOfWeatherMeasures);
}
    @FXML
    void sendRegistrationCode() throws SQLException {

        if(verificateDataFromUser.isEmail(registrationEmailTextField)==true
                && verificateDataFromUser.isPasswordStrength(registrationPasswordPasswordField,registrationAlertLabel)==true
                && jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText())==false
                && registrationPasswordPasswordField.getText().equals(registrationConfirmedPasswordPasswordField.getText())==true) {
            registerVBox.setVisible(false);
            register2VBox.setVisible(true);
            EmailToRegister emailToRegister = new EmailToRegister(registrationEmailTextField);
            Thread threadRegisterEmail = new Thread(emailToRegister);
            threadRegisterEmail.start();
            registerCode = emailToRegister.getRegistrationCode();
            registrationCodeAlertLabel.setVisible(false);
        }
        else if (verificateDataFromUser.isEmail(registrationEmailTextField)==false){
            registrationAlertLabel.setText("Nie prawidłowy adres E-mail");
            registrationAlertLabel.setVisible(true);
        }
        else if(jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText())==true){
            registrationAlertLabel.setText("Konto jest już zarejstrowane");
            registrationAlertLabel.setVisible(true);
        }
        else if( verificateDataFromUser.isPasswordStrength(registrationPasswordPasswordField,registrationAlertLabel)==false){
        }
        else if(!registrationPasswordPasswordField.equals(registrationConfirmedPasswordPasswordField)){
            registrationAlertLabel.setText("Hasła nie są jednakowe");
            registrationAlertLabel.setVisible(true);
        }



    }
    @FXML
    void sendResetPasswordCode() {
        EmailToResetPassword emailToResetPassword = new EmailToResetPassword(emailToResetPasswordTextField);
        if (verificateDataFromUser.isEmail(emailToResetPasswordTextField) == true) {
            Thread threadResetPasswordEmail = new Thread(emailToResetPassword);
            threadResetPasswordEmail.start();
            changePassword1VBox.setVisible(false);
            changePassword2VBox.setVisible(true);
            resetPasswordCode = emailToResetPassword.getResetPasswordCode();
            badCodeResetPasswordLabel.setVisible(false);
        }
        else
        {
            badEmailResetPasswordLabel.setVisible(true);
        }
    }
@FXML
void goToChangePasswordButton(){
        if(resetCodeTextField.getText().equals(resetPasswordCode)) {
            changePassword2VBox.setVisible(false);
            changePassword3VBox.setVisible(true);
        }
        else {
            badCodeResetPasswordLabel.setVisible(true);
        }
}
@FXML
void changePasswordButton() throws SQLException {
    if (verificateDataFromUser.isPasswordStrength(resetPasswordPasswordField, badPasswordLabel) == true) {
        if (resetPasswordPasswordField.getText().equals(conifrmedResetPasswordPasswordField.getText())) {
            if (jdbcQuery.changeUserPassword(emailToResetPasswordTextField, resetPasswordPasswordField, conifrmedResetPasswordPasswordField, badPasswordLabel) == true) {
                changePassword3VBox.setVisible(false);
                loginVBox.setVisible(true);
            }
        }
        else
            badPasswordLabel.setVisible(true);
            badPasswordLabel.setText("Hasła nie są jednakowe");
    }

}
    @FXML
    void switchOnForgotPasswordButton(){
        loginVBox.setVisible(false);
        changePassword1VBox.setVisible(true);
        badEmailResetPasswordLabel.setVisible(false);
    }


    @FXML
    void switchOnLoginButton(){
        changePassword1VBox.setVisible(false);
        changePassword2VBox.setVisible(false);
        changePassword3VBox.setVisible(false);
        registerVBox.setVisible(false);
        register2VBox.setVisible(false);
        badEmailOrPasswordLabel.setVisible(false);
        loginVBox.setVisible(true);
    }

}






