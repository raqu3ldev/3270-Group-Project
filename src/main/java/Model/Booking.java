package Model;

import java.time.LocalDateTime;

public class Booking {

    private int bookingId;
    private int userId;    // FK to users.user_id
    private int flightId;  // FK to flights.flight_id
    private LocalDateTime bookingDate;
    private String status; // e.g. "CONFIRMED", "CANCELLED"

    public Booking() {
    }

    public Booking(int bookingId, int userId, int flightId,
                   LocalDateTime bookingDate, String status) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

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

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", flightId=" + flightId +
                ", status='" + status + '\'' +
                '}';
    }

    public void setFlightNumber(String flightNumber) {
    }
    public void setFromCity(String fromCity) {
    }
    public void setToCity(String toCity) {
    }

    public void setDepartureTime(LocalDateTime localDateTime) {
    }
}

