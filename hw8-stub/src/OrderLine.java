import java.sql.*;
import java.util.ArrayList;

public class OrderLine {
    /**
     * Attributes: a Product, and a quantity (int)
     */
    Product product;
    int quantity;


    /**
     * Initializes the object with the product and quantity
     * @param product
     * @param quantity
     */
    public OrderLine(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Getters and Setters
     */
    public int getQuantity() {return quantity;}
    public Product getProduct() {return product;}
    public void setProduct(Product product) {this.product = product;}
    public void setQuantity(int quantity) {this.quantity = quantity;}


    /**
     * @return the subtotal of the line (item price x qty)
     */
    public double subTotal(){
        //TODO
        return this.product.price * this.getQuantity();
    }

    /**
     * @return the string representation of the order line.
     */
    public String toString(){
        return String.format("Line:{%5d x %s = $%10.2f}",this.getQuantity(),this.getProduct(), this.subTotal());
    }

    /**
     * Loads all the OrderLine objects for a specific Order
     * @param db the database connection
     * @param order the order to search the lines for. (can be shallowed loaded)
     * @return an arraylist of order lines.
     * HINT:
     *     Don't deep load the product, as it will be replaced at the Store object.
     */
    public static ArrayList<OrderLine> loadLinesForOrder(Connection db, Order order){
        //TODO
        ArrayList<OrderLine> orders = new ArrayList<>();

        try{
            // Prepare and execute query
            PreparedStatement query = db.prepareStatement
                    ("SELECT order_number, product_code, quantity FROM store.order_line WHERE order_number = ?");
            query.setString(1, order.getOrderNumber());
            ResultSet rows = query.executeQuery();
            // While there are new rows, assign a new product and fields to the arraylist
            while(rows.next()){
                orders.add(new OrderLine(new Product(rows.getString("product_code")),
                                                     rows.getInt("quantity")));
            }
            // close the rows and the query
            rows.close();
            query.close();
           // db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
