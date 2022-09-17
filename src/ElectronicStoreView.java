//Class representing the GUI for an electronic store
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.Arrays;

public class ElectronicStoreView extends Pane implements StoreView{
    private ListView<Product> stockList, mostPopList;
    private ListView<String> cartList;
    private Button resetBtn, addBtn, removeBtn, completeSaleBtn;
    private Label cartLbl;
    private TextField salesTxt, revenueTxt, moneyPerSaleTxt;

    //Get methods
    public Button getAddBtn() { return addBtn; }
    public Button getCompleteSaleBtn() { return completeSaleBtn; }
    public Button getRemoveBtn() { return removeBtn; }
    public Button getResetBtn() { return resetBtn; }
    public ListView<String> getCartList() { return cartList; }
    public ListView<Product> getStockList() { return stockList; }

    public ElectronicStoreView(){
        //Create the labels
        Label summaryLbl = new Label("Store Summary:");
        summaryLbl.relocate(50, 10);

        Label stockLbl = new Label("Store Stock:");
        stockLbl.relocate(305, 10);

        cartLbl = new Label();
        cartLbl.relocate(585, 10);

        Label salesLbl = new Label("# Sales:");
        salesLbl.relocate(20, 38);

        Label revenueLbl = new Label("Revenue:");
        revenueLbl.relocate(13, 72 );

        Label moneyPerSaleLbl = new Label("$ / Sale:");
        moneyPerSaleLbl.relocate(20, 108);

        Label mostPopLbl = new Label("Most Popular Items:");
        mostPopLbl.relocate(39, 140);

        //Create the TextFields
        salesTxt = new TextField();
        salesTxt.relocate(68, 32);
        salesTxt.setPrefSize(110, 30);

        revenueTxt = new TextField();
        revenueTxt.relocate(68, 66);
        revenueTxt.setPrefSize(110, 30);

        moneyPerSaleTxt = new TextField();
        moneyPerSaleTxt.relocate(68, 100);
        moneyPerSaleTxt.setPrefSize(110, 30);

        //Create ListViews
        stockList = new ListView<Product>();
        stockList.relocate(188, 32);
        stockList.setPrefSize(296, 301);

        cartList = new ListView<String>();
        cartList.relocate(494, 32);
        cartList.setPrefSize(296, 301);

        mostPopList = new ListView<Product>();
        mostPopList.relocate(10, 165);
        mostPopList.setPrefSize(168, 168);

        //Create buttons
        resetBtn = new Button("Reset Store");
        resetBtn.relocate(20, 340);
        resetBtn.setPrefSize(148,45);

        addBtn = new Button("Add to Cart");
        addBtn.relocate(262, 340);
        addBtn.setPrefSize(148, 45);

        removeBtn = new Button("Remove from Cart");
        removeBtn.relocate(494, 340);
        removeBtn.setPrefSize(148, 45);

        completeSaleBtn = new Button("Complete Sale");
        completeSaleBtn.relocate(642, 340);
        completeSaleBtn.setPrefSize(148, 45);

        //Add all components to pane
        //Set size of the pane
        getChildren().addAll(summaryLbl, stockLbl, cartLbl, salesLbl, revenueLbl, moneyPerSaleLbl,
                mostPopLbl, salesTxt, revenueTxt, moneyPerSaleTxt, resetBtn, addBtn, removeBtn,
                completeSaleBtn, mostPopList, stockList, cartList);
        setPrefSize(800, 400);
    }

    //Updates the view with an updated model
    //selectedStock represents the index of the selected stockList ListView item
    //selectedCart represents the index of the selected cartList ListView item
    public void update(ElectronicStore model, int selectedStock, int selectedCart){
        //Stores current cart data for display
        ArrayList<String> cartProducts = new ArrayList<String>();

        //Generates array of current cart items in String format
        //Sold quantity included with the product info
        for(Product p:model.getCart()){
            cartProducts.add(p.getCartQuantity() + " x " + p);
        }

        //Sets button functionality to disabled to start
        //Checks if buttons should be enabled as ListView items are updated, selected
        addBtn.setDisable(true);
        removeBtn.setDisable(true);
        completeSaleBtn.setDisable(true);
        if(selectedStock != -1){ addBtn.setDisable(false); }
        if(selectedCart != -1){ removeBtn.setDisable(false); }
        if(model.getCart().size() != 0){ completeSaleBtn.setDisable(false); }

        //Displays updates to all fields
        cartLbl.setText(String.format("Current cart: ($%.2f)", model.getCartValue()));
        salesTxt.setText(Integer.toString(model.getSales()));
        revenueTxt.setText(String.format("$%.2f", model.getRevenue()));
        moneyPerSaleTxt.setText(((model.getMoneyPerSale() != 0.0) ? String.format("%.2f", model.getMoneyPerSale()) : "N/A"));
        stockList.setItems(FXCollections.observableArrayList(model.getStock()));
        cartList.setItems(FXCollections.observableArrayList(cartProducts));
        cartList.getSelectionModel().select(selectedCart); //Maintains cart selection after a removal operation, for convenience
        if(model.getSales() == 0){
            mostPopList.setItems(FXCollections.observableArrayList(model.initiializeMostPopular()));
        }
        else{
            mostPopList.setItems(FXCollections.observableArrayList(model.getMostPopular()));
        }
    }
}