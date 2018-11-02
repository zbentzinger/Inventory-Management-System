package inventorymanagementsystem.Controllers;

import inventorymanagementsystem.Models.Inventory;
import inventorymanagementsystem.Models.Part;
import inventorymanagementsystem.Models.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductViewController implements Initializable {

    private String mainViewPath = "/inventorymanagementsystem/Views/MainView.fxml";
    private ObservableList<Part> potentialParts = FXCollections.observableArrayList();
    private int ID;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private TableView<Part> selectedPartTableView;
    @FXML private TableColumn<Part, Integer> selectedPartIDColumn;
    @FXML private TableColumn<Part, String> selectedPartNameColumn;
    @FXML private TableColumn<Part, Integer> selectedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> selectedPartPriceColumn;
    @FXML private TableView<Part> allPartsTableView;
    @FXML private TableColumn<Part, Integer> allPartsIDColumn;
    @FXML private TableColumn<Part, String> allPartsNameColumn;
    @FXML private TableColumn<Part, Integer> allPartsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> allPartsPriceColumn;
    @FXML private TextField IDTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField searchTextField;

    public void addButtonClicked() {

        potentialParts.add(
                allPartsTableView.getSelectionModel().getSelectedItem()
        );

        selectedPartTableView.setItems(potentialParts);

        allPartsTableView.getSelectionModel().clearSelection();

        addButton.setDisable(true);

    }

    public void cancelButtonClicked(ActionEvent event) throws IOException {

        // Rubric Section J1, all Delete and Cancel buttons must be confirmed by user.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            ActionEvent clickedEvent = event;
            this.loadMainView(clickedEvent);

        } else {

            alert.close();

        }

    }

    public void deleteButtonClicked() {

        // Rubric Section J1, all Delete and Cancel buttons must be confirmed by user.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Remove Part");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            potentialParts.remove(
                selectedPartTableView.getSelectionModel().getSelectedItem()
            );

            selectedPartTableView.getSelectionModel().clearSelection();

            deleteButton.setDisable(true);

        } else {

            alert.close();

        }

    }

    public void enableAddButton() {

        addButton.setDisable(false);

    }

    public void enableDeleteButton() {

        deleteButton.setDisable(false);

    }

    private void loadMainView(ActionEvent event) throws IOException {

        Parent mainView = FXMLLoader.load(getClass().getResource(mainViewPath));
        Scene mainViewScene = new Scene(mainView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();

    }

    public void saveButtonClicked(ActionEvent click) throws IOException {

        // Rubric Section J1, esure that product must have a part.
        if (potentialParts.size() == 0) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error Adding Product");
            alert.setHeaderText("There was an issue.");
            alert.setContentText("Please select at least one part.");
            alert.showAndWait();

        } else {

            String name = nameTextField.getText();
            String price = priceTextField.getText();
            String inv = inventoryTextField.getText();
            String min = minTextField.getText();
            String max = maxTextField.getText();

            Product newProduct = new Product(
                    ID,
                    name,
                    Double.parseDouble(price),
                    Integer.parseInt(inv),
                    Integer.parseInt(min),
                    Integer.parseInt(max)
            );

            Inventory.addProduct(newProduct);

            for (Part part : this.potentialParts) {

                newProduct.addAssociatedPart(part);

            }

            ActionEvent clickedEvent = click;
            this.loadMainView(clickedEvent);

            }

    }

    public void searchButtonClicked() {

        allPartsTableView.setItems(
                Inventory.searchParts(searchTextField.getText())
        );

    }

    private void setupTableViews() {

        // All Parts TableView
        allPartsIDColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("partID")
        );
        allPartsNameColumn.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name")
        );
        allPartsInventoryLevelColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("inStock")
        );
        allPartsPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Double>("price")
        );

        allPartsTableView.setItems(Inventory.getAllParts());
        allPartsTableView.setPlaceholder(new Label("No Parts Found"));

        // Selected Parts TableView
        selectedPartIDColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("partID")
        );
        selectedPartNameColumn.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name")
        );
        selectedPartInventoryLevelColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("inStock")
        );
        selectedPartPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Part, Double>("price")
        );

        selectedPartTableView.setPlaceholder(new Label("No Parts Added"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setupTableViews();

        ID = Inventory.getNewProductID();
        IDTextField.setText(Integer.toString(ID));
        IDTextField.setDisable(true);

        addButton.setDisable(true);
        deleteButton.setDisable(true);

    }    

}
