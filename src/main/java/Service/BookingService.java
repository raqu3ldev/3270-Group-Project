package Service;

import Model.Booking;
import Model.Flight;
import Database.BookingDAO;
import Database.FlightDAO;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;
    private FlightDAO flightDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.flightDAO = new FlightDAO();
    }


    public boolean bookFlight(int userId, int flightId) {
        try {
            Flight flight = flightDAO.getFlightById(flightId);
            if (flight == null || flight.isFull()) {
                return false;
            }

            boolean bookingCreated = bookingDAO.createBooking(userId, flightId);

            if (bookingCreated) {
                flightDAO.decrementAvailableSeats(flightId);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error booking flight: " + e.getMessage());
        }
    }


    public boolean hasBookingConflict(int userId, Flight newFlight) {
        try {
            return bookingDAO.hasBookingConflict(userId, newFlight);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking booking conflict: " + e.getMessage());
        }
    }


    public boolean cancelBooking(int bookingId) {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                return false;
            }

            boolean cancelled = bookingDAO.cancelBooking(bookingId);

            if (cancelled) {
                flightDAO.incrementAvailableSeats(booking.getFlightId());
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error cancelling booking: " + e.getMessage());
        }
    }


    public List<Booking> getUserBookings(int userId) {
        try {
            return bookingDAO.getUserBookings(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting user bookings: " + e.getMessage());
        }
    }


    public List<Booking> getAllBookings() {
        try {
            return bookingDAO.getAllBookings();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting all bookings: " + e.getMessage());
        }
    }
}
