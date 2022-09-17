//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import java.util.*;

public class ElectronicStore{
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private String name;
    private ArrayList<Product> stock; //Array to hold all products in stock
    private ArrayList<Product> cart; //Array to hold selected products as the current cart before buying
    private ArrayList<Product> stockCopy; //Array to keep record of all prodcuts and is unaffected by operations like stock is
    private ArrayList<Product> mostPopular; //Array to hold the three most sold items
    private double revenue;
    private double cartValue; //Monetary value of all items in the cart
    private int sales; //Stores how many complete sales have been made
    private double moneyPerSale;

    public ElectronicStore(String initName){
        name = initName;
        revenue = 0.0;
        cartValue = 0.0;
        sales = 0;
        moneyPerSale = 0.0;
        stock = new ArrayList<Product>();
        cart = new ArrayList<Product>();
        stockCopy = new ArrayList<Product>();
        mostPopular = new ArrayList<Product>();
    }

    //Get methods
    public String getName(){ return name; }
    public double getRevenue(){ return revenue; }
    public int getSales(){ return sales; }
    public double getCartValue(){ return cartValue; }
    public double getMoneyPerSale(){ return moneyPerSale; }
    public ArrayList<Product> getStock(){ return stock; }
    public ArrayList<Product> getCart(){ return cart; }
    public ArrayList<Product> getMostPopular(){ return mostPopular; }

    //Adds a product to the store's stock, returns true if successful and false if not
    //Cannot add duplicates to the store stock
    //Also adds to the stockCopy, so a trace of the products is available if one ejected from stock
    public boolean addProduct(Product p){
        if((stock.size() < MAX_PRODUCTS) && (!stock.contains(p))){
            stock.add(p);
            if(!stockCopy.contains(p)){
                stockCopy.add(p);
                return true;
            }
        }
        return false;
    }

    //Adds 1 unit of the product in the stock to the cart if not already in it, and returns true if successful
    //Removes product from stock if all its quantity is put in the cart
    public boolean addToCart(Product p){
        double value = p.sellUnits(1);
        if(value != 0.0) {
            cartValue += value;
            if (!cart.contains(p)) {
                cart.add(p);
            }
            if (p.getStockQuantity() == 0) {
                stock.remove(p);
            }
            return true;
        }
        return false;
    }

    //Adds 1 unit of the product in the cart to the stock if not already in it, and returns true if successful
    //Uses the string of the product in the cart and checks for the product in the cart array that equals it
    //Removes product from cart if all its quantity is put back into the stock
    public boolean removeFromCart(String item){
        item = item.replace(item.substring(0, 4), "").trim(); //Removes cartQuantity string and whitespace
        for(Product product:cart){
            if(product.toString().equals(item)){ //Finds product in cart array
                Product p = product;
                double value = p.returnUnits(1);
                if(value != 0.0) {
                    cartValue -= value;
                    if (!stock.contains(p)) {
                        addProduct(p);
                    }
                    if (p.getCartQuantity() == 0) {
                        cart.remove(p);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    //Initializes mostPopular array at beginning of program
    public ArrayList<Product> initiializeMostPopular(){
        mostPopular.clear();
        for(int i=0; i<3; i++){
            mostPopular.add(stockCopy.get(i));
        }
        return mostPopular;
    }

    //Clears previous array of most popular items
    //Sorts the soldItems array in ascending order of soldQuantity
    //Adds first three products soldItems and returns as mostPopular array
    public ArrayList<Product> findMostPopular(){
        mostPopular.clear();
        Collections.sort(stockCopy);
        for(int i=0; i<3; i++){
            if(i < stockCopy.size()){ //Adds if index exists
                mostPopular.add(stockCopy.get(i));
            }
        }
        return mostPopular;
    }

    //Updates store revenue by adding cartValue, increases total sales and calculates the average amount money per sale
    //Adds cart products to the soldItems array, sets each cart product's cart quantity to zero
    //Finds most popular items and clears the cart
    public boolean completeSale(){
        if(cart.size() > 0){
            revenue += cartValue;
            cartValue = 0.0;
            sales++;
            moneyPerSale = revenue/sales;

            //Set all products in cart to zero cart quantity
            //Adds products in cart to soldItems
            for(Product p: cart) {
                if(!stockCopy.contains(p)){
                    stockCopy.add(p);
                }
                p.setCartQuantity(0);
            }
            mostPopular = findMostPopular();
            cart.clear();
            return true;
        }
        return false;
    }

    //Resets store to original state
    public ElectronicStore reset(){
        return ElectronicStore.createStore();
    }

    public static ElectronicStore createStore(){
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", 15.5, false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", 23, true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", 8, false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", 12, true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
}