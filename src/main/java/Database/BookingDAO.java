package Database;

import Model.Booking;
import Model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookingDAO {


    //creates bookings
    public boolean createBooking(int userId, int flightId) throws SQLException {
        String query = "INSERT INTO bookings (user_id, flight_id, status) VALUES (?, ?, 'CONFIRMED')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            // This handles the UNIQUE constraint (user already booked this flight)
            return false;
        }
    }

    //checks booking conflicts
    public boolean hasBookingConflict(int userId, Flight newFlight) throws SQLException {
        String query = "SELECT b.*, f.departure_time, f.arrival_time " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id " +
                "WHERE b.user_id = ? AND b.status = 'CONFIRMED' " +
                "AND (b.flight_id = ? OR " +
                "(f.departure_time < ? AND f.arrival_time > ?))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, newFlight.getFlightId());
            stmt.setTimestamp(3, Timestamp.valueOf(newFlight.getArrivalTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(newFlight.getDepartureTime()));

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If any result, there's a conflict
        }
    }

    //cancels bookings
    public boolean cancelBooking(int bookingId) throws SQLException {
        String query = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //Gets all user bookings
    public List<Booking> getUserBookings(int userId) throws SQLException {
        String query = "SELECT b.*, f.flight_number, f.from_city, f.to_city, f.departure_time " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }

            return bookings;
        }
    }

    //Gets all booking for Admin User
    public List<Booking> getAllBookings() throws SQLException {
        String query = "SELECT b.*, f.flight_number, f.from_city, f.to_city, f.departure_time, " +
                "u.first_name, u.last_name " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id " +
                "JOIN users u ON b.user_id = u.user_id " +
                "ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }

            return bookings;
        }
    }

    //Gets booking by ID
    public Booking getBookingById(int bookingId) throws SQLException {
        String query = "SELECT b.*, f.flight_number, f.from_city, f.to_city, f.departure_time " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id " +
                "WHERE b.booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }

            return null;
        }
    }

    //Extracts bookings
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setFlightId(rs.getInt("flight_id"));

        Timestamp bookingDate = rs.getTimestamp("booking_date");
        booking.setBookingDate(bookingDate != null ? bookingDate.toLocalDateTime() : null);

        booking.setStatus(rs.getString("status"));

        // Flight details (from JOIN)
        booking.setFlightNumber(rs.getString("flight_number"));
        booking.setFromCity(rs.getString("from_city"));
        booking.setToCity(rs.getString("to_city"));

        Timestamp departure = rs.getTimestamp("departure_time");
        booking.setDepartureTime(departure != null ? departure.toLocalDateTime() : null);

        return booking;
    }
}
