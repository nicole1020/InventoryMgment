package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Nicole Mau
 */

//Per UML diagram initialize private access:
// private(id:int, name:String, price:double, stock:int, min:int, max:int)
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

 //Per UML diagram associatedParts:ObservableList<Part>
  private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

//Per UML diagram Public access:
    //public(Product(id:int, name:String, price:double, stock:int, min:int, max:int))
    
    public Product(int id, String name, double price, int stock,   int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        return false;
    }


    public  ObservableList<Part>  getAllAssociatedParts() {

        return associatedParts;
    }
    public void setAllAssociatedParts(ObservableList<Part> associatedParts) {

        this.associatedParts = associatedParts;

    }

        public int getMax () {
            return max;
        }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAssociatedPart(Part part) {
        this.associatedParts.addAll(part);
    }
}
