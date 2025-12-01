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

    /**
     * Book a flight for a user
     */
    public boolean bookFlight(int userId, int flightId) {
        try {
            // Check if flight has available seats
            Flight flight = flightDAO.getFlightById(flightId);
            if (flight == null || flight.isFull()) {
                return false;
            }

            // Create the booking
            boolean bookingCreated = bookingDAO.createBooking(userId, flightId);

            if (bookingCreated) {
                // Decrease available seats
                flightDAO.decrementAvailableSeats(flightId);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error booking flight: " + e.getMessage());
        }
    }

    /**
     * Check if user has a booking conflict with this flight
     * Conflict = same flight OR overlapping time
     */
    public boolean hasBookingConflict(int userId, Flight newFlight) {
        try {
            return bookingDAO.hasBookingConflict(userId, newFlight);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking booking conflict: " + e.getMessage());
        }
    }

    /**
     * Cancel a booking
     */
    public boolean cancelBooking(int bookingId) {
        try {
            // Get booking details to find the flight
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                return false;
            }

            // Cancel the booking
            boolean cancelled = bookingDAO.cancelBooking(bookingId);

            if (cancelled) {
                // Increment available seats back
                flightDAO.incrementAvailableSeats(booking.getFlightId());
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error cancelling booking: " + e.getMessage());
        }
    }

    /**
     * Get all bookings for a specific user
     */
    public List<Booking> getUserBookings(int userId) {
        try {
            return bookingDAO.getUserBookings(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting user bookings: " + e.getMessage());
        }
    }

    /**
     * Get all bookings (Admin only)
     */
    public List<Booking> getAllBookings() {
        try {
            return bookingDAO.getAllBookings();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting all bookings: " + e.getMessage());
        }
    }
}
