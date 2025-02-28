package Customer.CService;

import Customer.Interface.DiscountStrategy;

public class BookingService {
    private final vehicleBookingDAO bookingDAO;
    private final DiscountStrategy discountStrategy;
    private final UniqueIdGeneratorVehicle idGenerator;

    public BookingService(vehicleBookingDAO bookingDAO, DiscountStrategy discountStrategy, UniqueIdGeneratorVehicle idGenerator) {
        this.bookingDAO = bookingDAO;
        this.discountStrategy = discountStrategy;
        this.idGenerator = idGenerator;
    }

    public boolean bookVehicle(String vehicleName, String bookingDate, String bookingTime, String returnDate,
                               double rentPerDay, String customerName, String customerEmail) {
        double totalFare = calculateTotalFare(rentPerDay, bookingDate, returnDate, customerName);
        String uniqueId = idGenerator.generateUniqueId();

        // ✅ Using the Builder pattern here
        BookVehicleBooking booking = new BookVehicleBooking.Builder()
            .setVehicleName(vehicleName)
            .setUniqueId(uniqueId)
            .setBookingDate(bookingDate)
            .setBookingTime(bookingTime)
            .setReturnDate(returnDate)
            .setRentPerDay(String.valueOf(rentPerDay))
            .setCustomerName(customerName)
            .setTotalFare(String.valueOf(totalFare))
            .setEmail(customerEmail)
            .build();

        return bookingDAO.addBooking(booking);  // Call the method, expecting a boolean return
    }

    private double calculateTotalFare(double rentPerDay, String bookingDate, String returnDate, String customerName) {
        double totalFare = rentPerDay;
        if (bookingDAO.getBookingCount(customerName) >= 5) {
            totalFare = discountStrategy.applyDiscount(totalFare);
        }
        return totalFare;
    }
}
