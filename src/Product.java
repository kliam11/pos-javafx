//Base class for all products the store will sell
public class Product implements Comparable<Product>{
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int cartQuantity; //Used to represent how much of the product is in the cart

    public Product(double initPrice, int initQuantity){
        price = initPrice;
        stockQuantity = initQuantity;
        cartQuantity = 0;
    }

    //Get and set methods
    public int getStockQuantity(){
        return stockQuantity;
    }
    public int getCartQuantity() { return cartQuantity; }
    public void setCartQuantity(int cartQuantity) { this.cartQuantity = cartQuantity; }

    //Returns the total revenue (price * amount) if there are at least 1 item in stock
    //"Sells" units by converting stock units to sold units, increases cart quantity by 1
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount){
        if(stockQuantity >= amount){
            stockQuantity -= amount;
            soldQuantity += amount;
            cartQuantity++;
            return price * amount;
        }
        return 0.0;
    }

    //Returns total revenue ( price * amount) if there are at least 1 item in sold quanity
    //"Returns" sold units by converting back to stock units, decreases cart quantity by 1
    //Returns 0 otherwise (i.e., there is no return completed)
    public double returnUnits(int amount){
        if(soldQuantity >= amount){
            stockQuantity += amount;
            soldQuantity -= amount;
            cartQuantity--;
            return price * amount;
        }
        return 0.0;
    }

    //Used in Collections.sort(mostPopular)
    //Allows sorting in ascending order by product sold quantity
    @Override
    public int compareTo(Product p) {
        return p.soldQuantity - this.soldQuantity;
    }
}