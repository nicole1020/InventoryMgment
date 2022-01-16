package model;


/**
 *
 * @author Nicole Mau
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.SecureRandom;
public class Inventory {

    /** Per UML diagram: private allParts:ObservableList<Part>. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Per UML diagram: private allProducts:ObservableList<Product>. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Unique part and product ID generation initialized. */
    public static int uniquePartID;
    public static int uniqueProductID;

    /** Per project requirements generate unique Part ID.

    @param uniquePartID generates unique part id.
    @return uniquePartID returned.
     */
    public static int generatePartID(int uniquePartID) {
        Inventory.uniquePartID = uniquePartID;
        uniquePartID = (new SecureRandom().nextInt(9999999));
        return uniquePartID;
    }

    /** Per project requirements generate unique Product ID.

     @param uniqueProductID generates unique part id.
     @return uniqueProductID returned.
     */
    public static int generateProductID(int uniqueProductID) {
        Inventory.uniqueProductID = uniqueProductID;
        uniqueProductID = (new SecureRandom().nextInt(9999999));
        return uniqueProductID;
    }

    /** Per UML diagram: public addPart(newPart:Part)void.
     @param newPart addPart calls in newPart and adds to allParts ObservableArrayList.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Per UML diagram: public addProduct(newProduct:Product)void.
     @param newProduct addProduct calls in newPart and adds to allProducts ObservableArrayList.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Per UML diagram: public lookupPart(int partId):Part.
     *
     * @param partId  see search fields in main screen, add product and modify product controllers.
     * @return null.
     */
    public static Part lookupPart(int partId) {

        return null;
    }

    /** Per UML diagram: lookupProduct.

     @param productId see search field in main screen controllers.
     @return null.
     */
    public static Product lookupProduct(int productId){

        return null;
    }

    /** Per UML diagram. lookupPart by name.

      @param partName see search fields in main screen, add product and modify product controllers.
     @return null.
     */
    public static ObservableList<Part> lookupPart(String partName){

        return null;
    }

    /** Per UML diagram: lookupProduct by name.

     @param productName see search field in main screen controllers.
     @return null.
     */
    public static ObservableList<Product> lookupProduct(String productName){

        return null;
    }

    /** Per UML diagram: public updatePart(index:int, selectedPart:Part):void.

      @param index see modify part controller for deletion and recreation of new part.
      @param selectedPart part to be modified.
     */
    public static void updatePart(int index, Part selectedPart) {
        System.out.println(index);
        allParts.set(index, selectedPart);

    }

    /** Per UML diagram: public updateProduct(index:int, selectedProduct:Product):void.
     @param index see modify Product controller for deletion and recreation of new Product.
     @param selectedProduct part to be modified.
     */

    public static void updateProduct(int index, Product selectedProduct){

        allProducts.set(index, selectedProduct);
    }

    /** Per UML diagram: public deletePart(selectedPart:Part):boolean.

     @param selectedPart deletes selected part.
     @return false.
     */
    public static boolean deletePart(Part selectedPart) {
allParts.remove(selectedPart);
        return false;
    }

    /** Per UML diagram: public deleteProduct(selectedProduct:Product):boolean.

     @param selectedProduct deletes selected Product.
     @return false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return false;
    }

    /** Per UML diagram: getAllParts().
      @return allParts.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /** Per UML diagram: getAllProducts().
      @return allProducts. 
     */
    public static ObservableList<Product> getAllProducts() {
       return allProducts;}

    /** Per project recommendations: dummy test data for InHouse Parts, OutSourced Parts, and Products.

     */
    public static void addTestData() {
      /*InHouse Parts*/
        Part  AA = new InHouse(9, "Sunflower Seeds", 1.00, 110, 10, 1000, 21);
            Inventory.addPart(AA);

        Inventory.addPart(new InHouse(13, "Tomato Seeds", 1.20, 120, 12, 1200, 123));
        /*OutSourced Parts*/
        Part  AB = new OutSourced(11, "Snapdragon Seeds 10", 1.10, 110, 11, 1100, "ABC Seeds");
            Inventory.addPart(AB);

        Inventory.addPart(new OutSourced(14, "Concrete Flower Pots", 14.00, 200, 50, 1200, "Unique Flower Pots"));

        Inventory.addPart(new OutSourced(20, "Organic Potting Soil", 1.00, 700, 50, 1200, "1st Soil Company"));
        /*Products*/
        Product A1 = new Product(1001, "Sunflower Plant", 30.00, 100, 20, 500);
            Inventory.addProduct(A1);

        Product A2 = new Product(1002, "Garden Gloves 20", 21.00, 200, 10, 210);
            Inventory.addProduct(A2);
/*Prints out all of the test Parts */
        System.out.println("Part Test Data:");
        for(int i = 0; i < Inventory.allParts.size(); i++) {
            System.out.println(Inventory.allParts.get(i).getName());
        }
        System.out.println("");
/*Prints out all of the test Products */
        System.out.println("Products Test Data:");
        for(int i = 0; i < Inventory.allProducts.size(); i++) {
            System.out.println(Inventory.allProducts.get(i).getName());

        }
        System.out.println("");
}}