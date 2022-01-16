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
import javafx.stage.Stage;
import main.Main;
import model.Inventory;
import model.Part;
import model.InHouse;
import model.OutSourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyPartController implements Initializable {

    /**
     * To Main (cancel button), and save Part button.
     */
    public Button modifyToMain;
    public Button saveModifyPart;

    /**
     * Radio buttons and toggle group.
     */
    public ToggleGroup tgroupmodifypart;
    public RadioButton modifyRadioInHouse;
    public RadioButton modifyRadioOutsourced;


    /**
     * Modify Part Form (last text box changes upon radio button change).
     */
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartPrice;
    public TextField modifyPartMax;
    public TextField modifyPartMin;
    public TextField modifyPartID;
    public TextField modifyLastTextBoxInput;
    public Label modifyResultsLabel;
    public Label modifyLastTextBox;

    /**
     * SelectedPart is part from Main Screen Selection.
     */
    public Part selectedPart;


    /**
     * Set data from selectedPart to modify part screen text fields.
     *
     * @param thePart sets data from main screen part selection to modify part text fields for editing.
     */
    public void modifyingPart(Part thePart) {

        selectedPart = thePart;
        modifyPartID.setText(String.valueOf(selectedPart.getId()));
        modifyPartName.setText(String.valueOf(selectedPart.getName()));
        modifyPartPrice.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartInv.setText(String.valueOf(selectedPart.getStock()));
        modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
        modifyPartMax.setText(String.valueOf(selectedPart.getMax()));

        if (selectedPart instanceof InHouse) {
            System.out.println("In House Selected");
            modifyLastTextBoxInput.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
            modifyLastTextBox.setText("Machine ID");
            modifyRadioInHouse.setSelected(true);
        } else if (selectedPart instanceof OutSourced) {
            System.out.println("Outsourced Selected");
            modifyLastTextBoxInput.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
            modifyLastTextBox.setText("Company Name");
            modifyRadioOutsourced.setSelected(true);
        }
    }

    /**
     * Cancel button action event sends user back to main screen.
     *
     * @param actionEvent sends user back to main screen- cancel pressed.
     * @throws IOException must not be null.
     */
    public void onModifyToMain(ActionEvent actionEvent) throws IOException {
        if (modifyRadioInHouse.isSelected()) {
            System.out.println("In House Selected");
        } else if (modifyRadioOutsourced.isSelected()) {
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

    }

    /**
     * radio button for InHouse sets proper prompts and label for the last text box- this is the default selection.
     *
     * @param actionEvent default selection for radio buttons-InHouse.
     */
    public void onModifyRadio(ActionEvent actionEvent) {
        modifyLastTextBox.setText("Machine ID");
        modifyLastTextBoxInput.setPromptText("Enter Machine ID");
    }

    /**
     * adds data from add part screen to main screen Part table.
     *
     * @param actionEvent save part button pushed- sends data to main screen saves a new part and deletes the original.
     * @throws IOException           if null.
     * @throws NumberFormatException if wrong data type is entered.
     */
    public void onSaveModifyPart(ActionEvent actionEvent) throws IOException {
        if (!minLessThanMax()) {
            System.out.println("min is not less than max");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between min and max");
            alert.showAndWait();
        }
        if (minLessThanMax()) {
            {
                System.out.println("saving part");
            }

            try {

                if (modifyRadioInHouse.isSelected()) {

                    Part selectedPart = new InHouse(
                            Integer.parseInt(modifyPartID.getText()),
                            modifyPartName.getText(),
                            Double.parseDouble(modifyPartPrice.getText()),
                            Integer.parseInt(modifyPartInv.getText()),
                            Integer.parseInt(modifyPartMin.getText()),
                            Integer.parseInt(modifyPartMax.getText()),
                            Integer.parseInt(modifyLastTextBoxInput.getText()));
                    Inventory.addPart(selectedPart);
                } else if (modifyRadioOutsourced.isSelected()) {

                    Part selectedPart = new OutSourced(
                            Integer.parseInt(modifyPartID.getText()),
                            modifyPartName.getText(),
                            Double.parseDouble(modifyPartPrice.getText()),
                            Integer.parseInt(modifyPartInv.getText()),
                            Integer.parseInt(modifyPartMin.getText()),
                            Integer.parseInt(modifyPartMax.getText()),
                            modifyLastTextBoxInput.getText());
                    Inventory.addPart(selectedPart);
                }

                /* show main screen with updated data from Modify Part*/
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 619);
                stage.setTitle("Back to Main Screen");
                stage.setScene(scene);
                stage.show();
                Inventory.deletePart(selectedPart);
                System.out.println("Save Modify Part Selected- Part Deleted");
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
    public void onModifyRadioSecond(ActionEvent actionEvent) {
        modifyLastTextBox.setText("Company Name");
        modifyLastTextBoxInput.setPromptText("Enter Company Name");
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
            String nameinput = modifyPartName.getText();
            System.out.println("" + nameinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + modifyPartName + " is not a string.");
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
            int inventoryinput = Integer.parseInt(modifyPartInv.getText());
            System.out.println("Machine ID " + inventoryinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + modifyPartInv + " is not a number.");
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
            double priceinput = Double.parseDouble(modifyPartPrice.getText());
            System.out.println("Price set to " + "$" + priceinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + modifyPartPrice + " is not a double.");
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
            int maxinput = Integer.parseInt(modifyPartMax.getText());
            System.out.println("Machine ID " + maxinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + modifyPartMax + " is not a number.");
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
            int mininput = Integer.parseInt(modifyPartMin.getText());
            System.out.println("Machine ID " + mininput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + modifyPartMin + " is not a number.");

            return false;
        }
    }

    /**
     * throws error if user's InHouse input is not an integer.
     */
    @FXML
    private void isIntegerTest4() {

        if (modifyRadioInHouse.isSelected()) {
            System.out.println("In House Selected-must contain only integers");
        } else if (modifyRadioOutsourced.isSelected()) {
            System.out.println("Outsourced Selected");
        }
    }

    /**
     * Throws an error if user's Min input is greater than Max input or inventory isn't between values of min and max.
     *
     * @return true.
     */
    @FXML
    public boolean minLessThanMax() {
        if ((Integer.parseInt(modifyPartMin.getText()) < Integer.parseInt(modifyPartInv.getText())) && (Integer.parseInt(modifyPartInv.getText()) < Integer.parseInt(modifyPartMax.getText()))) {

            modifyResultsLabel.setText("");
            return true;
        }
        else if ((Integer.parseInt(modifyPartMin.getText()) > Integer.parseInt(modifyPartInv.getText())) || (Integer.parseInt(modifyPartInv.getText()) > Integer.parseInt(modifyPartMax.getText()))) {

            modifyResultsLabel.setText("Inventory must fall between min and max.");
            System.out.println("Error: Inventory must be greater than min and less than max. ");

            return false;
        }

        return false;
    }
}