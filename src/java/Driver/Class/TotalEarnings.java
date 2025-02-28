
package Driver.Class;


public class TotalEarnings {
    private String uniqueId;
    private String vehiclename;
    private String bookingdate;
    private String returneddate;
    private String customername;
    private String totalFare;
    private String fine;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getReturneddate() {
        return returneddate;
    }

    public void setReturneddate(String returneddate) {
        this.returneddate = returneddate;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }
    
    
     public double getTotalAmount() {
        double fare = (totalFare != null && !totalFare.isEmpty()) ? Double.parseDouble(totalFare) : 0.0;
        double fineAmount = (fine != null && !fine.isEmpty()) ? Double.parseDouble(fine) : 0.0;
        return fare + fineAmount;
    }
     
     
    
}
