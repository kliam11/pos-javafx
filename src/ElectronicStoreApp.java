//Class representing the controller (event handling) for an electronic store
import javafx.event.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {
    private ElectronicStore model;

    public ElectronicStoreApp(){
        model = ElectronicStore.createStore();
    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        // Create the view
        ElectronicStoreView view = new ElectronicStoreView();
        aPane.getChildren().add(view);

        //Set the stage
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
        view.update(model, -1, -1);

        //EventHandlers for ListViews
        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model, view.getStockList().getSelectionModel().getSelectedIndex(), -1);
            }
        });
        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model, -1, view.getCartList().getSelectionModel().getSelectedIndex());
            }
        });

        // EventHandlers for buttons
        view.getResetBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.update(model = model.reset(), -1, -1);
            }
        });
        view.getAddBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.addToCart(view.getStockList().getSelectionModel().getSelectedItem());
                view.update(model, view.getStockList().getSelectionModel().getSelectedIndex(), -1);

                //Disables button if a ListView selection is null
                //(i.e. after all of a stock item is transferred to Current Cart from the stock ListView, in which the stock item will disappear)
                if(view.getStockList().getSelectionModel().getSelectedItem() == null){
                    view.update(model, -1, -1);
                }
            }
        });
        view.getRemoveBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String selection = view.getCartList().getSelectionModel().getSelectedItem();
                model.removeFromCart(selection);
                view.update(model, -1, view.getCartList().getSelectionModel().getSelectedIndex());

                //Disables button if a ListView selection is null
                //(i.e. after all of a cart item is returned to stock from the Current Cart ListView, in which the cart item will disappear)
                if(view.getCartList().getSelectionModel().getSelectedItem() == null){
                    view.update(model, -1, -1);
                }
            }
        });
        view.getCompleteSaleBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.completeSale();
                view.update(model, -1, -1);
            }
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}
