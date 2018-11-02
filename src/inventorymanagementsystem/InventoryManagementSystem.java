package inventorymanagementsystem;

import inventorymanagementsystem.Models.InhousePart;
import inventorymanagementsystem.Models.Inventory;
import inventorymanagementsystem.Models.OutsourcedPart;
import inventorymanagementsystem.Models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManagementSystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        
        stage.setTitle("C482 - Inventory Management System");
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
    
    private static void setupBaseData() {
        
        Inventory.addPart(
                new InhousePart(
                        Inventory.getNewPartID(),
                        "Test InHouse Part 1",
                        4.00,
                        5,
                        0,
                        10,
                        20
                )
        );
        
        Inventory.addPart(
                new OutsourcedPart(
                        Inventory.getNewPartID(),
                        "Test Outsourced Part 1",
                        2.15,
                        5,
                        0,
                        10,
                        "Dell"
                )
        );
        
        Inventory.addPart(
                new OutsourcedPart(
                        Inventory.getNewPartID(),
                        "Test Outsourced Part 3",
                        8.30,
                        10,
                        0,
                        10,
                        "Toshiba"
                )
        );

    }

    public static void main(String[] args) {
                
        setupBaseData();

        launch(args);

    }
    
}
