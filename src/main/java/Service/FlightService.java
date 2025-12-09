package Service;

import Model.Flight;
import Database.FlightDAO;
import java.time.LocalDate;
import java.util.List;


public class FlightService extends BaseService{
    private FlightDAO flightDAO;

    public FlightService() {
        this.flightDAO = new FlightDAO();
    }

    /**
     * Search for flights based on criteria
     * @param fromCity - departure city (can be empty for all)
     * @param toCity - arrival city (can be empty for all)
     * @param flightDate - date of flight (can be null for all)
     * @return List of matching flights
     */
    public List<Flight> searchFlights(String fromCity, String toCity, LocalDate flightDate) {
        try {
            return flightDAO.searchFlights(fromCity, toCity, flightDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error searching flights: " + e.getMessage());
        }
    }

    /**
     * Get all flights
     */
    public List<Flight> getAllFlights() {
        try {
            return flightDAO.getAllFlights();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting all flights: " + e.getMessage());
        }
    }

    /**
     * Get a specific flight by ID
     */
    public Flight getFlightById(int flightId) {
        try {
            return flightDAO.getFlightById(flightId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting flight: " + e.getMessage());
        }
    }

    /**
     * Add a new flight (Admin only)
     */
    public boolean addFlight(Flight flight) {
        try {
            return flightDAO.addFlight(flight);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding flight: " + e.getMessage());
        }
    }

    /**
     * Update an existing flight (Admin only)
     */
    public boolean updateFlight(Flight flight) {
        try {
            return flightDAO.updateFlight(flight);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating flight: " + e.getMessage());
        }
    }

    /**
     * Delete a flight (Admin only)
     */
    public boolean deleteFlight(int flightId) {
        try {
            return flightDAO.deleteFlight(flightId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting flight: " + e.getMessage());
        }
    }
}

