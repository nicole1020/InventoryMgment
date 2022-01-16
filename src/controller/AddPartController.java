package controller;
/**
 *
 * @author Nicole Mau
 */



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.OutSourced;
import model.Part;
import model.Inventory;
import model.InHouse;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.uniquePartID;
/**
 * Initializes controller class.
 * */
public class AddPartController implements Initializable {

    /**
     * To Main (cancel button), and savePart button.
     */
    public Button savePart;
    public Button toMain;

    /**
     * Radio buttons and toggle group.
     */
    public ToggleGroup tgroupaddpart;
    public RadioButton radioInHouse;
    public RadioButton radioOutsourced;

    /**
     * Add Part Form (last text box changes upon radio button change).
     */
    public TextField partId;
    public TextField partName;
    public TextField partInv;
    public TextField partPrice;
    public TextField partMax;
    public TextField partMin;
    public Label lastTextBox;
    public TextField lastTextBoxInput;
    public Label addPartResultsLabel;

    /**
     * Unique Part ID.
     */
    private int newPartID;

    /**
     * Cancel button action event sends user back to main screen.
     *
     * @param actionEvent sends user back to main screen- cancel pressed.
     * @throws IOException must not be null.
     */
    public void onToMain(ActionEvent actionEvent) throws IOException {

        if (radioInHouse.isSelected()) {
            System.out.println("In House Selected");
        } else if (radioOutsourced.isSelected()) {
            System.out.println("Outsourced Selected");

        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Request to go back to Main Screen, any input will not be saved");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 619);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Initializes controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + " in initialize!");
        newPartID = Inventory.generatePartID(uniquePartID);
        System.out.println("Part ID Generated: " + newPartID);


    }

    /**
     * radio button for InHouse sets proper prompts and label for the last text box- this is the default selection.
     *
     * @param actionEvent default selection for radio buttons-InHouse.
     */
    public void onRadio(ActionEvent actionEvent) {
        lastTextBox.setText("Machine ID");
        lastTextBoxInput.setPromptText("Enter Machine ID");

    }

    /**
     * adds data from add part screen to main screen Part table.
     *
     * @param actionEvent save part button pushed- sends data to main screen.
     * @throws IOException           if null.
     * @throws NumberFormatException if wrong data type is entered.
     */
    public void onSavePart(ActionEvent actionEvent) throws IOException {
        if (!minLessThanMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between min and max");
            alert.showAndWait();

        } else if (minLessThanMax()) {
            {
                System.out.println("saving part");
            }
            try {

                if (radioInHouse.isSelected()) {
                    System.out.println("In House Selected");

                    Part newPart = new InHouse(newPartID, "", 0.00, 0, 0, 9, 0);
                    newPart.setName(partName.getText());
                    newPart.setPrice(Double.parseDouble(partPrice.getText()));
                    newPart.setStock(Integer.parseInt(partInv.getText()));
                    newPart.setMin(Integer.parseInt(partMin.getText()));
                    newPart.setMax(Integer.parseInt(partMax.getText()));
                    ((InHouse) newPart).setMachineID(Integer.parseInt(lastTextBoxInput.getText()));

                    Inventory.addPart(newPart);
                    addPartResultsLabel.setText("New InHouse Part Added");
                } else if (radioOutsourced.isSelected()) {
                    System.out.println("Outsourced Selected");
                    Part newPart = new OutSourced(newPartID, "", 0.00, 0, 0, 9, "");
                    newPart.setName(partName.getText());
                    newPart.setPrice(Double.parseDouble(partPrice.getText()));
                    newPart.setStock(Integer.parseInt(partInv.getText()));
                    newPart.setMin(Integer.parseInt(partMin.getText()));
                    newPart.setMax(Integer.parseInt(partMax.getText()));
                    ((OutSourced) newPart).setCompanyName(lastTextBoxInput.getText());

                    Inventory.addPart(newPart);
                    addPartResultsLabel.setText("New OutSourced Part Added");
                }

                /** show main screen with updated data from Add Part. */
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 619);
                stage.setTitle("Back to Main Screen");
                stage.setScene(scene);
                stage.show();

            } catch (NumberFormatException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Enter valid inputs");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * OutSourced radio button sets proper label and prompt in last text box.
     *
     * @param actionEvent if user clicks OutSourced radio button the last text box changes label and prompt.
     */
    public void onRadioSecond(ActionEvent actionEvent) {
        lastTextBox.setText("Company");
        lastTextBoxInput.setPromptText("Enter Company Name");

    }

    /**
     * throws an error if user's name input is not a string.
     *
     * @param actionEvent tests if string entered.
     * @return true.
     */
    @FXML
    public boolean isStringTest(ActionEvent actionEvent) {
        try {
            String nameinput = partName.getText();
            System.out.println("" + nameinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + partName + " is not a string.");
            return false;
        }
    }

    /**
     * throws error if user's inventory input is not an integer.
     *
     * @param actionEvent tests for integer data type.
     * @return true.
     */
    @FXML
    public boolean isIntegerTest1(ActionEvent actionEvent) {
        try {
            int inventoryinput = Integer.parseInt(partInv.getText());
            System.out.println("Machine ID " + inventoryinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + partInv + " is not a number.");
            return false;
        }
    }

    /**
     * throws error if user's Price input is not a Double.
     *
     * @param actionEvent tests if double is entered.
     * @return true.
     */
    @FXML
    public boolean isDoubleTest(ActionEvent actionEvent) {
        try {
            double priceinput = Double.parseDouble(partPrice.getText());
            System.out.println("Price set to " + "$" + priceinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + partPrice + " is not a double.");
            return false;
        }

    }

    /**
     * throws error if user's max input is not an integer.
     *
     * @param actionEvent tests if Integer is entered.
     * @return true.
     */
    @FXML
    public boolean isIntegerTest2(ActionEvent actionEvent) {
        try {
            int maxinput = Integer.parseInt(partMax.getText());
            System.out.println("Machine ID " + maxinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + partMax + " is not a number.");
            return false;
        }
    }


    /**
     * throws error if user's min input is not an integer.
     *
     * @param actionEvent tests if Integer is entered.
     * @return true.
     */
    @FXML
    private boolean isIntegerTest3(ActionEvent actionEvent) {
        try {
            int mininput = Integer.parseInt(partMin.getText());
            System.out.println("Machine ID " + mininput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + partMin + " is not a number.");
            return false;
        }
    }

    /**
     * throws error if user's InHouse input is not an integer.
     */
    @FXML
    private void isIntegerTest4() {

        if (radioInHouse.isSelected()) {
            System.out.println("In House Selected-must contain only integers");
        } else if (radioOutsourced.isSelected()) {
            System.out.println("Outsourced Selected");
        }
    }


    /**
     Throws an error if user's Min input is greater than Max input or inventory isn't between values of min and max.

   @return true.
     */
    @FXML
    private boolean minLessThanMax() {
        if ((Integer.parseInt(partMin.getText()) < Integer.parseInt(partInv.getText())) && (Integer.parseInt(partInv.getText()) < Integer.parseInt(partMax.getText()))) {

            addPartResultsLabel.setText("");
            return true;
        }
        else if ((Integer.parseInt(partMin.getText()) > Integer.parseInt(partInv.getText())) || (Integer.parseInt(partInv.getText()) > Integer.parseInt(partMax.getText()))) {

            addPartResultsLabel.setText("Inventory must fall between min and max.");
            System.out.println("Error: Inventory must be greater than min and less than max. ");

            return false;
        }

        return false;
    }
}




