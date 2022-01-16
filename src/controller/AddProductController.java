package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Nicole Mau
 */

public class AddProductController implements Initializable {
/** Add Product Form-user input. */
    public TextField productID;
    public TextField productName;
    public TextField productInv;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public Label addProductResultsLabel;
    /** Part Search text box. */
    public TextField productSearchField;
    /** Part table. */
    public TableView partTable;
    public TableColumn partTablePartIDCol;
    public TableColumn partTablePartNameCol;
    public TableColumn partTableStockCol;
    public TableColumn partTablePriceCol;


    /** Associated Parts Table- named productTable. */
    public TableView productTable;
    public TableColumn productTablePartIDCol;
    public TableColumn productTablePartNameCol;
    public TableColumn productTablePartStockCol;
    public TableColumn productTablePriceCol;

    /** Buttons-delete and add associated part, cancel, and save product. */
    public Button addPartButton;
    public Button removeAssociatedPart;
    public Button toMain;
    public Button saveProduct;
    /** unique Product ID. */
    private int newProductID;
    private int uniqueProductID;
    /** store associatedParts for products. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /** adds data from partTable to productTable upon user selection.
     @param actionEvent selected parts added to table when add part button pushed */
    public void onAddPartButton(ActionEvent actionEvent) {
        associatedParts.add((Part) partTable.getSelectionModel().getSelectedItem());
        productTable.setItems(associatedParts);

    }
    /** adds data from add product screen to main screen Product table.
     @param actionEvent when save button pushed new product is saved to main screen product table.
     @throws IOException if null.
     */

    public void onSaveProduct(ActionEvent actionEvent) throws IOException {
        if (!minLessThanMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between min and max");
            alert.showAndWait();

        } else if (minLessThanMax()) {
            {

                System.out.println("saving part");
            }
            try {

                {
                    Product newProduct = new Product(newProductID, "", 0.0, 0, 0, 0);
                    newProduct.setName(productName.getText());
                    newProduct.setPrice(Double.parseDouble(productPrice.getText()));
                    newProduct.setStock(Integer.parseInt(productInv.getText()));
                    newProduct.setMin(Integer.parseInt(productMin.getText()));
                    newProduct.setMax(Integer.parseInt(productMax.getText()));
                    newProduct.setAllAssociatedParts(associatedParts);
                    Inventory.addProduct(newProduct);
                    System.out.println("Size: " + associatedParts.size());
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Enter valid inputs");
                alert.showAndWait();
            }

            //   /** show main screen with updated data from Add Product. */
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 800, 619);
            stage.setTitle("Back to Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Cancel button action event sends user back to main screen.
    @param actionEvent cancel clicked sends user to main screen.
     @throws IOException if null. */
    public void onToMain(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Request to go back to Main Screen, any input will not be saved");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 619);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** throws an error if user's name input is not a string.
     @param actionEvent tests to see if name input is a string.
     @return returns error if it is the wrong data type.
     */
    public boolean isStringTest(ActionEvent actionEvent) {
        try {
            String nameinput = productName.getText();
            System.out.println("" + nameinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + productName + " is not a string.");
            return false;
        }
    }
    /** throws an error if user's stock input is not an Integer.
     @param actionEvent to see if inventory input is an integer.
     @return returns error if it is the wrong data type. */
    public boolean isIntegerTest1(ActionEvent actionEvent) {
        try {
            int inventoryinput = Integer.parseInt(productInv.getText());
            System.out.println("Machine ID " + inventoryinput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + productInv + " is not a number.");
            return false;
        }

    }
    /** throws an error if user's price input is not a Double.
     @param actionEvent to see if Price input is an Double.
     @return returns error if it is the wrong data type.
     */
    public boolean isDoubleTest(ActionEvent actionEvent) {
        try {
        double priceinput = Double.parseDouble(productPrice.getText());
        System.out.println("Price set to " + "$" + priceinput);
        return true;
    }catch(NumberFormatException e){
        System.out.println("Error" + productPrice + " is not a double.");
        return false;
    }}

    /** throws an error if user's price input is not more than sum of all associated parts.
     @param actionEvent test to see if product price is less than part price.
     @return returns error if it is the wrong data type. */
public boolean totalPriceTest(ActionEvent actionEvent){
                    try {
                        double totalPrice = Double.compare(Double.parseDouble(productPrice.getText()), Double.parseDouble(partTablePriceCol.getText()));
                        System.out.println("Total value is" + "$"+totalPrice);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error" + productPrice+ "is not more than the sum of" +productTablePriceCol);
                   return false;
                    }
                }

    /** throws an error if user's productMax input is not a Integer.
     @param actionEvent test to see if max is an integer.
     @return returns error if it is the wrong data type. */
    public boolean isIntegerTest2(ActionEvent actionEvent){
                    try {
                        int maxinput = Integer.parseInt(productMax.getText());
                        System.out.println("Machine ID " + maxinput);
                        return true;
                    } catch (NumberFormatException e) {
                        System.out.println("Error" + productMax + " is not a number.");
                        return false;
                    }
                }
    /** throws an error if user's productMin input is not a Integer.
     @param actionEvent test to see if min is an integer.
     @return returns error if it is the wrong data type. */
    public boolean isIntegerTest3(ActionEvent actionEvent) {
        try {
            int mininput = Integer.parseInt(productMin.getText());
            System.out.println("Machine ID " + mininput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error" + productMin + " is not a number.");
            return false;
        }
    }

    /** throws an error if user's productMin input is not less than productMax.
     @return true.
     */
    @FXML
    private boolean  minLessThanMax() {
        if ((Integer.parseInt(productMin.getText()) < Integer.parseInt(productInv.getText())) && (Integer.parseInt(productInv.getText()) < Integer.parseInt(productMax.getText()))) {

            addProductResultsLabel.setText("");
            return true;
        } else if ((Integer.parseInt(productMin.getText()) > Integer.parseInt(productInv.getText())) || (Integer.parseInt(productInv.getText()) > Integer.parseInt(productMax.getText()))) {

            addProductResultsLabel.setText("Inventory must fall between min and max.");
            System.out.println("Error: Inventory must be greater than min and less than max. ");

            return false;
        }

        return false;
    }
    /** remove associated parts from selection in product table(associated parts table).
     @param actionEvent remove selected associated parts from table. */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Request to delete, result is permanent");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObservableList<Part> selectedPart, deletePart;
            deletePart = productTable.getItems();
            selectedPart = productTable.getSelectionModel().getSelectedItems();
            selectedPart.forEach(deletePart::remove);
            System.out.println("Delete associated Part Clicked and removed associated part");
        }
    }
    /**
     Initializes controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + " in initialize!");

        newProductID = Inventory.generateProductID(uniqueProductID);

        System.out.println("Product ID Generated: " + newProductID);

        ObservableList<Part> part = Inventory.getAllParts();
        partTable.setItems(part);


        partTablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //disable deletePart button until user clicks on a part
        this.removeAssociatedPart.setDisable(true);
        //disable the modifyPart button until user clicks on a part
        this.addPartButton.setDisable(true);
        
        productTablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTablePartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
      //  partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    /** Disables removeAssociatedPart button until user clicks on a part.
     @param mouseEvent disables removeAssociatedPart button until a part is selected. */
    public void onProductSelected(MouseEvent mouseEvent) {
        this.removeAssociatedPart.setDisable(false);

    }

    /** Disables addPartButton button until user clicks on a part.

     @param mouseEvent disables removeAssociatedPart button until a part is selected. */

    public void onPartSelected(MouseEvent mouseEvent) {
        this.addPartButton.setDisable(false);
    }

    /** Parts Search -search by name or ID.
     @param keyEvent search field for parts.
     */

    public void onProductSearchField(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Enter Pressed on parts table");
            String q = productSearchField.getText();

            ObservableList<Part>parts = searchByPartName(q);

            if(parts.size()==0) {
                try {
                    int id = Integer.parseInt(q);
                    Part p = getAPartWithPartNumber(id);
                    if (p != null)
                        parts.add(p);
                } catch (NumberFormatException e) {
                    //ignore catch
                }
            }
            partTable.setItems(parts);
            addProductResultsLabel.setText(parts.size() + " parts returned.");
            productSearchField.setText("");
        }
    }

    private ObservableList<Part> searchByPartName(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part p: allParts){
            if (p.getName().contains(partName)){
                namedParts.add(p);

            }
        }
        return namedParts;
    }

    private Part getAPartWithPartNumber (int id){
        ObservableList<Part> allParts = Inventory.getAllParts();
        for( int i=0; i< allParts.size(); i++){
            Part p= allParts.get(i);
            if (p.getId()==id){
                return p;
            }
        }

        return null;
    }

}





  

