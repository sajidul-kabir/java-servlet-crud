package entity;

public class Order {

    private int id;
    private String customer_name;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getAddress() {
        return address;
    }

    public String getProduct() {
        return product;
    }

    public double getTransaction() {
        return transaction;
    }

    public Order(String customer_name, String address, String product, double transaction) {
        this.customer_name = customer_name;
        this.address = address;
        this.product = product;
        this.transaction = transaction;
    }

    public Order(int id, String customer_name, String address, String product, double transaction) {
        this.id = id;
        this.customer_name = customer_name;
        this.address = address;
        this.product = product;
        this.transaction = transaction;
    }

    private String product;
    private double transaction;
}
