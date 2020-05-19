import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.aksingh.owmjapis.api.APIException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class StationBrowser {
    private void findStation(TextField inputText, ArrayList<String> inputStations, ListView<String> stationsListView) throws APIException, ParseException, IOException {
        boolean finded=false;
        ArrayList<String> findedCities=new ArrayList<>();
        String input = inputText.getText().toLowerCase();
        String finalInput = "";
        for (int i = 0; i < input.length(); i++) {
            if (i == 0) {
                finalInput += input.charAt(i);
                finalInput = finalInput.toUpperCase();
            }
            if (i > 0) {
                if (input.charAt(i) == 32) {
                    String bigletter = " " + input.substring(i + 1, i + 2).toUpperCase();
                    finalInput += bigletter;
                    i++;
                } else {
                    finalInput += input.charAt(i);
                }

            }
        }
        if(finalInput.length()>0) {
            for (int i = 0; i < inputStations.size(); i++) {
                if (inputStations.get(i).startsWith(finalInput)) {

                    finded = true;
                    findedCities.add(inputStations.get(i));
                }
            }
            if (finded == true) {
                inputText.setStyle("-fx-control-inner-background: green;");
                stationsListView.getItems().clear();
                stationsListView.getItems().addAll(findedCities);
                stationsListView.getSelectionModel().selectFirst();

            } else if (finded == false) {
                inputText.setStyle("-fx-control-inner-background: red;");
                stationsListView.getItems().removeAll();
                stationsListView.getItems().clear();
                stationsListView.getItems().addAll(inputStations);
            }
        }
        else
        {
            inputText.setStyle("");
            stationsListView.getItems().removeAll();
            stationsListView.getItems().clear();
            stationsListView.getItems().addAll(inputStations);
        }
    }
    public void searchByNameOnWriteInTextField(TextField textField,ArrayList<String>inputStations,ListView<String>stationsListView) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                try {
                    findStation(textField, inputStations, stationsListView);
                } catch (APIException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            });
    }
}
