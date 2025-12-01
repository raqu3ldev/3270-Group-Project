package Model;
import java.time.LocalDateTime;


public class Booking {
    private int bookingId;
    private int userId;
    private int flightId;
    private LocalDateTime bookingDate;
    private String status; // CONFIRMED, CANCELLED

    // Additional fields for displaying booking details (from JOIN query)
    private String flightNumber;
    private String fromCity;
    private String toCity;
    private LocalDateTime departureTime;

    // Default Constructor
    public Booking() {}

    // Basic Constructor
    public Booking(int bookingId, int userId, int flightId, LocalDateTime bookingDate, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Full Constructor (with flight details)
    public Booking(int bookingId, int userId, int flightId, LocalDateTime bookingDate,
                   String status, String flightNumber, String fromCity, String toCity,
                   LocalDateTime departureTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.flightNumber = flightNumber;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", flightNumber='" + flightNumber + '\'' +
                ", from='" + fromCity + '\'' +
                ", to='" + toCity + '\'' + ", status='" + status + '\'' + '}';
    }


}
