package inventorymanagementsystem.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static int newPartID = 1;
    private static int newProductID = 1;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> products = FXCollections.observableArrayList();

    public static void addProduct(Product product) {

        products.add(product);

    }

    public static boolean removeProduct(int ID) {

        int index = 0;

        for (Product product : products) {

            if (product.getProductID() == ID) {

                products.remove(index);
                return true;

            }

            index++;

        }

        return false;

    }

    public static Product lookupProduct(int ID) {

        int index = 0;

        for (Product product : products) {

            if (product.getProductID() == ID) {

               return products.get(index);

            }

            index++;

        }

        return null;

    }

    public static void updateProduct(int ID) {
        /**
         * Unused, functionality handled in ModifyProductViewController.
         */
    }

    public static void addPart(Part part) {

        allParts.add(part);

    }

    public static boolean deletePart(Part part) {

        int partID = part.getPartID();

        int index = 0;

        for (Part p : allParts) {

            if (p.getPartID() == partID) {

                allParts.remove(index);
                return true;

            }

            index++;

        }

        return false;

    }

    public static Part lookupPart(int partID) {

        int index = 0;

        for (Part part : allParts) {

            if (part.getPartID() == partID) {

               return allParts.get(index);

            }

            index++;

        }

        return null;

    }

    public static void updatePart(int ID) {
        /**
         * Unused, functionality handled in ModifyPartViewController.
         */
    }

    // Custom Methods.

    public static ObservableList<Part> getAllParts() {

        return allParts;

    }

    public static ObservableList<Product> getAllProducts() {

        return products;

    }

    public static int getNewPartID() {

        return newPartID++;

    }

    public static int getNewProductID() {

        return newProductID++;

    }

    public static ObservableList<Product> searchProducts(String searchString) {

        searchString = searchString.replaceAll("\\s+","");
        ObservableList<Product> found = FXCollections.observableArrayList();

        for (Product prod : products) {

            String prodStr = new String();
            prodStr += Double.toString(prod.getProductID());
            prodStr += prod.getName();
            prodStr += Integer.toString(prod.getInStock());
            prodStr += Double.toString(prod.getPrice());
            prodStr = prodStr.replaceAll("\\s+","");

            if (prodStr.toLowerCase().contains(searchString.toLowerCase())) {

                found.add(prod);

            }

        }

        return found;

    }

    public static ObservableList<Part> searchParts(String searchString) {

        searchString = searchString.replaceAll("\\s+","");
        ObservableList<Part> found = FXCollections.observableArrayList();

        for (Part part : allParts) {

            String partStr = new String();
            partStr += Double.toString(part.getPartID());
            partStr += part.getName();
            partStr += Integer.toString(part.getInStock());
            partStr += Double.toString(part.getPrice());
            partStr = partStr.replaceAll("\\s+","");

            if (partStr.toLowerCase().contains(searchString.toLowerCase())) {

                found.add(part);

            }

        }

        return found;

    }

}
