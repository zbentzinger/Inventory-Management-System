package inventorymanagementsystem.Controllers;

import inventorymanagementsystem.Models.Inventory;
import inventorymanagementsystem.Models.Part;
import inventorymanagementsystem.Models.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainViewController implements Initializable {

    private String viewPath = "/inventorymanagementsystem/Views/";

    @FXML private Button exitButton;

    // Parts subscreen
    @FXML private Button deletePartButton;
    @FXML private Button modifyPartButton;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField searchPartTextField;

    // Products subscreen
    @FXML private Button deleteProductButton;
    @FXML private Button modifyProductButton;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TextField searchProductTextField;

    public void addPartButtonClicked(ActionEvent event) throws IOException {

        Parent addPartView = FXMLLoader.load(getClass().getResource(viewPath + "AddPartView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();

    }

    public void addProductButtonClicked(ActionEvent event) throws IOException {

        Parent addProductView = FXMLLoader.load(getClass().getResource(viewPath + "AddProductView.fxml"));
        Scene addProductViewScene = new Scene(addProductView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductViewScene);
        window.show();

    }

    public void deletePartButtonClicked() {
        
        // Rubric Section J1, all Delete and Cancel buttons must be confirmed by user.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            ObservableList<Part> selectedParts, allParts;

            allParts = partTableView.getItems();

            selectedParts = partTableView.getSelectionModel().getSelectedItems();

            for (Part part : selectedParts) {

                allParts.remove(part);
                Inventory.deletePart(part);

            }

            partTableView.getSelectionModel().clearSelection();
            deletePartButton.setDisable(true);
            modifyPartButton.setDisable(true);

        } else {

            alert.close();

        }

    }

    public void deleteProductButtonClicked() {
        
        // Rubric Section J1, all Delete and Cancel buttons must be confirmed by user.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            ObservableList<Product> selectedProducts, allProducts;

            allProducts = productTableView.getItems();

            selectedProducts = productTableView.getSelectionModel().getSelectedItems();

            for (Product prod : selectedProducts) {

                allProducts.remove(prod);
                Inventory.removeProduct(prod.getProductID());

            }

            productTableView.getSelectionModel().clearSelection();
            deleteProductButton.setDisable(true);
            modifyProductButton.setDisable(true);

        } else {

            alert.close();

        }

    }

    public void enablePartButtons() {

        deletePartButton.setDisable(false);
        modifyPartButton.setDisable(false);

    }

    public void enableProductButtons() {

        deleteProductButton.setDisable(false);
        modifyProductButton.setDisable(false);

    }

    public void exitButtonClicked(ActionEvent event) {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    public void modifyPartButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewPath + "ModifyPartView.fxml"));
        Parent modifyPartView = loader.load();

        Scene modifyPartViewScene = new Scene(modifyPartView);

        ModifyPartViewController controller = loader.getController();
        controller.initPart(partTableView.getSelectionModel().getSelectedItem().getPartID());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyPartViewScene);
        window.show();

    }

    public void modifyProductButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewPath + "ModifyProductView.fxml"));
        Parent modifyProductView = loader.load();

        Scene modifyProductViewScene = new Scene(modifyProductView);

        ModifyProductViewController controller = loader.getController();
        controller.initProduct(productTableView.getSelectionModel().getSelectedItem().getProductID());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyProductViewScene);
        window.show();

    }

    public void partSearchButtonClicked() {

        partTableView.setItems(
                Inventory.searchParts(searchPartTextField.getText())
        );

    }

    public void productSearchButtonClicked() {

        productTableView.setItems(
                Inventory.searchProducts(searchProductTextField.getText())
        );

    }

    private void setupTableViews() {

        // Parts TableView
        partIDColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("partID")
        );
        partNameColumn.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name")
        );
        partInventoryLevelColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("inStock")
        );
        partPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Double>("price")
        );

        partTableView.setItems(Inventory.getAllParts());
        partTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        partTableView.setPlaceholder(new Label("No Parts Found"));

        // Products TableView
        productIDColumn.setCellValueFactory(
                new PropertyValueFactory<Product, Integer>("productID")
        );
        productNameColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("name")
        );
        productInventoryLevelColumn.setCellValueFactory(
                new PropertyValueFactory<Product, Integer>("inStock")
        );
        productPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Product, Double>("price")
        );

        productTableView.setItems(Inventory.getAllProducts());
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productTableView.setPlaceholder(new Label("No Products Found"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setupTableViews();

        // Disable Modify and Delete buttons until part/product selected
        this.deletePartButton.setDisable(true);
        this.modifyPartButton.setDisable(true);
        this.deleteProductButton.setDisable(true);
        this.modifyProductButton.setDisable(true);

    }

}
