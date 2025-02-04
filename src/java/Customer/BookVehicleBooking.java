package Customer;

public class BookVehicleBooking {
    private String vehicleName;
    private String bookingDate;
    private String bookingTime;
    private String returnDate;
    private String rentPerDay;
    private String customerName;
    private String totalFare;
    private String email;
    
    public BookVehicleBooking(String vehicleName, String bookingDate, String bookingTime, String returnDate, String rentPerDay, String customerName, String totalFare,String email) {
        this.vehicleName = vehicleName;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.returnDate = returnDate;
        this.rentPerDay = rentPerDay;
        this.customerName = customerName;
        this.totalFare = totalFare;
        this.email = email;
    }

    public String getVehicleName() { return vehicleName; }
    public String getBookingDate() { return bookingDate; }
    public String getBookingTime() { return bookingTime; }
    public String getReturnDate() { return returnDate; }
    public String getRentPerDay() { return rentPerDay; }
    public String getCustomerName() { return customerName; }
    public String getTotalFare() {return totalFare; }
    public String getemail() {return email;}
}
