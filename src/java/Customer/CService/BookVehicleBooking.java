package Customer.CService;

public class BookVehicleBooking {

    private String vehicleName;
    private String uniqueId;
    private String bookingDate;
    private String bookingTime;
    private String returnDate;
    private String rentPerDay;
    private String customerName;
    private String totalFare;
    private String email;

    // Private constructor to enforce the use of the Builder
    private BookVehicleBooking(Builder builder) {
        this.vehicleName = builder.vehicleName;
        this.uniqueId = builder.uniqueId;
        this.bookingDate = builder.bookingDate;
        this.bookingTime = builder.bookingTime;
        this.returnDate = builder.returnDate;
        this.rentPerDay = builder.rentPerDay;
        this.customerName = builder.customerName;
        this.totalFare = builder.totalFare;
        this.email = builder.email;
    }

    // Getters
    public String getVehicleName() { return vehicleName; }
    public String getBookingDate() { return bookingDate; }
    public String getBookingTime() { return bookingTime; }
    public String getReturnDate() { return returnDate; }
    public String getRentPerDay() { return rentPerDay; }
    public String getCustomerName() { return customerName; }
    public String getTotalFare() { return totalFare; }
    public String getEmail() { return email; }
    public String getUniqueId() { return uniqueId; }

    public static class Builder {
        private String vehicleName;
        private String uniqueId;
        private String bookingDate;
        private String bookingTime;
        private String returnDate;
        private String rentPerDay;
        private String customerName;
        private String totalFare;
        private String email;

        public Builder setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
            return this;
        }

        public Builder setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

        public Builder setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Builder setBookingTime(String bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder setReturnDate(String returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder setRentPerDay(String rentPerDay) {
            this.rentPerDay = rentPerDay;
            return this;
        }

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setTotalFare(String totalFare) {
            this.totalFare = totalFare;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public BookVehicleBooking build() {
            return new BookVehicleBooking(this);
        }
    }
}
