package controller;

/**
 *
 * @author Nicole Mau
 */

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import javafx.beans.Observable;
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
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

/** Parts table. */
    public TableView<Part> partsTable;
    public TableColumn partsPartIDCol;
    public TableColumn partsPartNameCol;
    public TableColumn partsInventoryUnitCol;
    public TableColumn partsPriceCostPerUnitCol;
    /** Products table. */
    public TableView<Product> productsTable;
    public TableColumn productsProductIDCol;
    public TableColumn productsProductNameCol;
    public TableColumn productsInventoryUnitCol;
    public TableColumn productsPriceCostPerUnitCol;
    /** Part table buttons. */
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    /** Search field for Parts. */
    public TextField partsTextField;
    /** Search field for Products. */
    public TextField productsTextField;
    /** Product table buttons. */
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;

    public static TilePane partsTilePane;
    public TilePane productsTilePane;
    public Button exitButton;
    /** Part table results label (displays Part counter under left most column). */
    public TextField resultsLBL;
    /** Product table results label (displays Product counter under left most column). */
    public TextField resultsLBLproducts;

    /** Add part button pushed, switches to Add part screen.

     @param actionEvent Add part button pushed, switches to Add part screen.
     @throws IOException if null.
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/AddPartScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 610);
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Modify part button pushed, switches to modify part screen.

     @param actionEvent modify part button pushed, switches to modify part screen.
     @throws IOException if null.
     */
    public void onModifyPart(ActionEvent actionEvent)throws IOException  {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ModifyPartScreen.fxml"));
            Parent ModifyPartScreen = loader.load();
            ModifyPartController controller = loader.getController();
            controller.modifyingPart(partsTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(ModifyPartScreen, 600, 610);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Deletes Part from Main Screen Part table upon selection and delete button press.

     @param actionEvent Deletes Part from Main Screen Part table upon selection and delete button press.
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Request to delete, result is permanent");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObservableList<Part> selectedPart, deletePart;
            deletePart = partsTable.getItems();
            selectedPart = partsTable.getSelectionModel().getSelectedItems();
            selectedPart.forEach(deletePart::remove);
            System.out.println("Delete Part Clicked and removed part");
        }
    }

    /** Add Product button pushed and add product screen loaded.

     @param actionEvent add product button pushed and add product screen loaded.
     @throws IOException if null.
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/AddProductScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1100, 700);
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    /** Modify Product button pushed, switches to Modify Product screen.

     @param actionEvent Modify Product button pushed, switches to Modify Product screen.
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ModifyProductScreen.fxml"));
        Parent ModifyProductScreen= loader.load();
        ModifyProductController controller = loader.getController();
        controller.modifyingProduct(productsTable.getSelectionModel().getSelectedItem());
        System.out.println("Modify Product Clicked");
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(ModifyProductScreen, 1100, 700);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Deletes Product from Main Screen Product table upon selection and delete button press.

     @param actionEvent Deletes Product from Main Screen Product table upon selection and delete button press.
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product p = productsTable.getSelectionModel().getSelectedItem();
        if (p.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete product it has parts");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Request to delete, result is permanent");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deleteProduct(p);
            System.out.println("Delete Product Clicked");

        }
    }

    /** Search for parts by name or id.
     *
     * @param keyEvent searches part table search field for part by id or name.
     */
    public void lookupPart(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Enter Pressed on parts table");
            String q = partsTextField.getText();

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
            partsTable.setItems(parts);
            resultsLBL.setText(parts.size() + " parts returned.");
            partsTextField.setText("");
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

    /** Disables delete and modify part buttons until a part is selected.
     @param mouseEvent Disables deletePart and modifyPart buttons until a part is selected in the part table.
     */
    public void partIsSelected(MouseEvent mouseEvent) {
        this.deletePart.setDisable(false);
        this.modifyPart.setDisable(false);
    }
    /** Search for product by name or id.
      @param keyEvent searches product table search field for product by id or name.
     */
    public void onProductsTextField(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Enter Pressed on Products table");
            String q = productsTextField.getText();

            ObservableList<Product>products = searchByProductName(q);

            if(products.size()==0) {
                try {
                    int id = Integer.parseInt(q);
                    Product pr = getAProductWithProductNumber(id);
                    if (pr != null)
                        products.add(pr);
                } catch (NumberFormatException e) {
                    //ignore catch
                }
            }
            productsTable.setItems(products);
            resultsLBLproducts.setText(products.size() + " products returned.");
            productsTextField.setText("");
        }
    }


    private ObservableList<Product> searchByProductName(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product pr: allProducts){
            if (pr.getName().contains(productName)){
                namedProducts.add(pr);

            }
        }
        return namedProducts;
    }

    private Product getAProductWithProductNumber (int id){
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for( int i=0; i< allProducts.size(); i++){
            Product pr= allProducts.get(i);
            if (pr.getId()==id){
                return pr;
            }
        }

        return null;
    }

    /** disables delete and modify product buttons until product is selected.

    @param mouseEvent disables delete and modify product buttons until product is selected.
     */
    public void productIsSelected(MouseEvent mouseEvent) {
        this.deleteProduct.setDisable(false);
        this.modifyProduct.setDisable(false);
    }

    /**
     * Initializes controller class.
     * */
     @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
/** Part Table initialized. */
        ObservableList<Part>parts = Inventory.getAllParts();
        partsTable.setItems(parts);
        resultsLBL.setText(parts.size() + " parts returned.");

        partsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryUnitCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

         /** Disables deletePart button until user clicks on a part. */
         this.deletePart.setDisable(true);
         /** Disables modifyPart button until user clicks on a part. */
         this.modifyPart.setDisable(true);
/** Product Table initialized. */
        ObservableList<Product>products = Inventory.getAllProducts();
        productsTable.setItems(products);
        resultsLBLproducts.setText(products.size()+ " products returned.");

        productsProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryUnitCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));


         /** Disables deleteProduct button until user clicks on a part. */
         this.deleteProduct.setDisable(true);

         /** disables modifyProduct button until user clicks on a product. */

         this.modifyProduct.setDisable(true);

    }

/** Per PA requirements, custom exit button on main screen to close program upon action.
  @param actionEvent upon exit Button pressed, program closes. */
    public void onExitButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }
}

