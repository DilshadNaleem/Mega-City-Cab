
package Customer.CService;


public class Vehicle {
    private String vehicleName;
    private String image;
    private double price;

    public Vehicle(String vehicleName, String image, double price) {
        this.vehicleName = vehicleName;
        this.image = image;
        this.price = price;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }
}

