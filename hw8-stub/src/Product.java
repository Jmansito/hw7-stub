import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Product {
    /**
     * Attributes code and description (both strings) and the product price (double)
     */
    String code;
    String description;
    double price;


    /**
     * Initializes the object with only the code (for later load)
     * @param code
     */
    public Product(String code){this.code = code;}

    /**
     * Initializes the objects with all code, description and price.
     * Price should be a non-negative number.
     * @param code
     * @param description
     * @param price
     */
    public Product(String code, String description, double price){
        this.code = code;
        this.description = description;
        this.price = price;
    }

    /**
     * Getters and Setters.
     * price must be non-negative. IllegalArgumentException if the price is negative.
     */
    public double getPrice() {return price;}
    public String getCode() {return code;}
    public String getDescription() {return description;}
    public void setCode(String code) {this.code = code;}
    public void setDescription(String description) {this.description = description;}
    public void setPrice(double price) {this.price = price;}

    /**
     * @return Product price representation.
     */
    public String toString(){
        return String.format("Product:[%-20s|%-30s|%10.2f]",this.getCode(),this.getDescription(),this.getPrice());
    }

    /**
     * Loads the current product object using the code.
     * @param db the database connection
     */
    public void load(Connection db){
        //TODO
        try{
            // Prepare and execute query
            PreparedStatement query = db.prepareStatement
                    ("SELECT code, description, price FROM product WHERE code = ?");
            query.setString(1, this.code);
            ResultSet rows = query.executeQuery();
            // While there are new rows, assign values to the Product object
            while(rows.next()){
                this.code = rows.getString("code");
                this.price = rows.getDouble("price");
                this.description = rows.getString("description");
            }
            // close the rows and the query
            rows.close();
            query.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Loads all the products in the DB
     * @param db the database connection
     * @return an arraylist of products
     * HINT:
     *      don't use the object load.
     */
    public static ArrayList<Product> loadAll(Connection db){
        //TODO
        ArrayList<Product> products = new ArrayList<>();
        try{
            // Prepare and execute query
            PreparedStatement query = db.prepareStatement
                    ("SELECT code, description, price FROM product");
            ResultSet rows = query.executeQuery();
            // While there are new rows, assign a new product and fields to the arraylist
            while(rows.next()){
                products.add(new Product(rows.getString("code"),
                        rows.getString("description"),
                        rows.getDouble("price")));
            }
            // close the rows and the query
            rows.close();
            query.close();
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
