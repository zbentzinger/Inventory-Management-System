package inventorymanagementsystem.Controllers;

import inventorymanagementsystem.Models.InhousePart;
import inventorymanagementsystem.Models.Inventory;
import inventorymanagementsystem.Models.OutsourcedPart;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartViewController implements Initializable {

    private String mainViewPath = "/inventorymanagementsystem/Views/MainView.fxml";
    private int ID;

    // Radio buttons
    @FXML final ToggleGroup group = new ToggleGroup();
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private RadioButton inHouseRadioButton;

    // Form
    @FXML private Label categoryLabel;
    @FXML private TextField IDTextView;       
    @FXML private TextField nameTextView;        
    @FXML private TextField inventoryTextView;        
    @FXML private TextField priceTextView;        
    @FXML private TextField maxTextView;        
    @FXML private TextField minTextView;
    @FXML private TextField categoryTextView;

    public void cancelButtonClicked(ActionEvent event) throws IOException {

        // Rubric Section J1, all Delete and Cancel buttons must be confirmed by user.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

    public void inHouseRadioButtonClicked(ActionEvent event) {

        categoryLabel.setText("Machine ID");
        categoryTextView.setPromptText("Machine ID");
        categoryTextView.setText("");

    }

    private void loadMainView(ActionEvent event) throws IOException {

        Parent mainView = FXMLLoader.load(getClass().getResource(mainViewPath));
        Scene mainViewScene = new Scene(mainView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();

    }

    public void outsourcedRadioButtonClicked(ActionEvent event) {

        categoryLabel.setText("Company Name");
        categoryTextView.setPromptText("Company Name");
        categoryTextView.setText("");

    }

    public void saveButtonClicked(ActionEvent event) throws IOException {

        String name = nameTextView.getText();
        String price = priceTextView.getText();
        String inv = inventoryTextView.getText();
        String min = minTextView.getText();
        String max = maxTextView.getText();
        String category = categoryTextView.getText();

        if (outsourcedRadioButton.isSelected()) {

            Inventory.addPart(
                new OutsourcedPart(
                        ID,
                        name,
                        Double.parseDouble(price),
                        Integer.parseInt(inv),
                        Integer.parseInt(min),
                        Integer.parseInt(max),
                        category
                )
            );

        } else {

            // Inhouse is default and there are only two radio buttons.
            Inventory.addPart(
                new InhousePart(
                        ID,
                        name,
                        Double.parseDouble(price),
                        Integer.parseInt(inv),
                        Integer.parseInt(min),
                        Integer.parseInt(max),
                        Integer.parseInt(category)
                )
            );
        }

        ActionEvent clickedEvent = event;
        this.loadMainView(clickedEvent);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ID = Inventory.getNewPartID();

        // Set resonable defaults on page load
        outsourcedRadioButton.setToggleGroup(group);
        inHouseRadioButton.setToggleGroup(group);
        inHouseRadioButton.setSelected(true);
        categoryLabel.setText("Machine ID");
        categoryTextView.setPromptText("Machine ID");
        IDTextView.setText(Integer.toString(ID));
        IDTextView.setDisable(true);

    }    

}
