import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;


public class TextFieldRestrict {
    public void limitCharsForTextField(TextField textField, int LIMIT) { //Method dont let user write more than LIMIT chars
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (textField.getText().length() >= LIMIT) {
                        textField.setText(textField.getText().substring(0, LIMIT));
                    }
                }
            }
        });
    }
    public void onlyPlusDigitsInTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() { //Method let user write only numbers or .
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[0-9]*\\.?[0-9]*")){
                    textField.setText(oldValue);
                }
            }
        });
    }
    public void onlyDigitsInTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() { //Method let user write only numbers or .
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[-]?[0-9]*\\.?[0-9]*")){

                        textField.setText(oldValue);


                }
            }
        });
    }

    }