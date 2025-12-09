package Database;

import Model.Flight;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    /**
     * Search flights based on criteria
     */
    public List<Flight> searchFlights(String fromCity, String toCity, LocalDate flightDate) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM flights WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        // Add filters if provided
        if (fromCity != null && !fromCity.trim().isEmpty()) {
            query.append(" AND LOWER(from_city) LIKE LOWER(?)");
            parameters.add("%" + fromCity + "%");
        }

        if (toCity != null && !toCity.trim().isEmpty()) {
            query.append(" AND LOWER(to_city) LIKE LOWER(?)");
            parameters.add("%" + toCity + "%");
        }

        if (flightDate != null) {
            query.append(" AND DATE(departure_time) = ?");
            parameters.add(flightDate);
        }

        query.append(" ORDER BY departure_time");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<Flight> flights = new ArrayList<>();

            while (rs.next()) {
                flights.add(extractFlightFromResultSet(rs));
            }

            return flights;
        }
    }

    /**
     * Get all flights
     */
    public List<Flight> getAllFlights() throws SQLException {
        String query = "SELECT * FROM flights ORDER BY departure_time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Flight> flights = new ArrayList<>();

            while (rs.next()) {
                flights.add(extractFlightFromResultSet(rs));
            }

            return flights;
        }
    }

    //Gets flight by ID
    public Flight getFlightById(int flightId) throws SQLException {
        String query = "SELECT * FROM flights WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractFlightFromResultSet(rs);
            }

            return null;
        }
    }

    //Adds Flight
    public boolean addFlight(Flight flight) throws SQLException {
        String query = "INSERT INTO flights (flight_number, from_city, to_city, " +
                "departure_time, arrival_time, total_seats, available_seats, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getFromCity());
            stmt.setString(3, flight.getToCity());
            stmt.setTimestamp(4, Timestamp.valueOf(flight.getDepartureTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(flight.getArrivalTime()));
            stmt.setInt(6, flight.getCapacity());
            stmt.setInt(7, flight.getAvailableSeats());
            stmt.setDouble(8, flight.getPrice());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //UpdateFlight
    public boolean updateFlight(Flight flight) throws SQLException {
        String query = "UPDATE flights SET flight_number = ?, from_city = ?, to_city = ?, " +
                "departure_time = ?, arrival_time = ?, total_seats = ?, " +
                "available_seats = ?, price = ? WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getFromCity());
            stmt.setString(3, flight.getToCity());
            stmt.setTimestamp(4, Timestamp.valueOf(flight.getDepartureTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(flight.getArrivalTime()));
            stmt.setInt(6, flight.getCapacity());
            stmt.setInt(7, flight.getAvailableSeats());
            stmt.setDouble(8, flight.getPrice());
            stmt.setInt(9, flight.getFlightId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //Deletes flights
    public boolean deleteFlight(int flightId) throws SQLException {
        String query = "DELETE FROM flights WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //Decrements available seating
    public boolean decrementAvailableSeats(int flightId) throws SQLException {
        String query = "UPDATE flights SET available_seats = available_seats - 1 " +
                "WHERE flight_id = ? AND available_seats > 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //Increments Available Seats
    public boolean incrementAvailableSeats(int flightId) throws SQLException {
        String query = "UPDATE flights SET available_seats = available_seats + 1 " +
                "WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    //Extracts Flights from result set
    private Flight extractFlightFromResultSet(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt("flight_id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setFromCity(rs.getString("from_city"));
        flight.setToCity(rs.getString("to_city"));

        Timestamp departure = rs.getTimestamp("departure_time");
        Timestamp arrival = rs.getTimestamp("arrival_time");

        flight.setDepartureTime(departure != null ? departure.toLocalDateTime() : null);
        flight.setArrivalTime(arrival != null ? arrival.toLocalDateTime() : null);

        flight.setCapacity(rs.getInt("total_seats"));
        flight.setAvailableSeats(rs.getInt("available_seats"));
        flight.setBasePrice(rs.getDouble("price"));

        return flight;
    }
}
