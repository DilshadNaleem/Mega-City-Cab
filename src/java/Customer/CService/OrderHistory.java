package Customer.CService;

public class OrderHistory {
    private int bookingId;
    private String uniqueId;
    private String vehicleName;
    private String bookingDate;
    private String returnDate;
    private String bookingTime;
    private String customerName;
    private String totalFare;
    private String rentPerDay;
    private String status;

    // Getters and Setters
    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getBookingTime() { return bookingTime; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getTotalFare() { return totalFare; }
    public void setTotalFare(String totalFare) { this.totalFare = totalFare; }

    public String getRentPerDay() { return rentPerDay; }
    public void setRentPerDay(String rentPerDay) { this.rentPerDay = rentPerDay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getBookingId() {return bookingId;}
    public void setBookingId(int bookingId) {this.bookingId = bookingId;}
    
    public String getUniqueId() {return uniqueId;}
    public void setUniqueId(String uniqueId) {this.uniqueId = uniqueId;}
}
