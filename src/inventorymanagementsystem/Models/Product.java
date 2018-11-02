package inventorymanagementsystem.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product(int ID, String name, double price, int inStock, int min, int max) {

        this.productID = ID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getName() {

        return name;

    }

    public void setPrice(double price) {

        this.price = price;

    }

    public double getPrice() {

        return price;

    }

    public void setInStock(int inStock) {

        this.inStock = inStock;

    }

    public int getInStock() {

        return inStock;

    }

    public void setMin(int min) {

        this.min = min;

    }

    public int getMin() {

        return min;

    }

    public void setMax(int max) {

        this.max = max;

    }

    public int getMax() {

        return max;

    }

    public void addAssociatedPart(Part part) {

        associatedParts.add(part);

    }

    public boolean removeAssociatedPart(int ID) {

        int index = 0;

        for (Part part : associatedParts) {

            if (part.getPartID() == ID) {

                associatedParts.remove(index);
                return true;

            }

            index++;

        }

        return false;

    }

    public Part lookupAssociatedPart(int ID) {

        int index = 0;

        for (Part part : associatedParts) {

            if (part.getPartID() == ID) {

               return associatedParts.get(index);

            }

            index++;

        }

        return null;

    }

    public void setProductID(int ID) {

        this.productID = ID;

    }

    public int getProductID() {

        return productID;

    }

    // Custom Methods:

    public ObservableList<Part> getParts() {

        return associatedParts;

    }

}
