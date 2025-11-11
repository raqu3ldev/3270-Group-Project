package org.example;

import java.util.ArrayList;

public class Customer {
    private String firstname;
    private String lastname;
    private String address;
    private String zipcode;
    private String state;
    private String username;
    private String password;
    private String email;
    private String ssn;
    private String securityQuestion;
    private String securityAnswer;
    private ArrayList<String> bookedFlights = new ArrayList<>();

    public Customer(String firstName, String lastName, String address, String zipcode, String state,
                    String username, String password, String email, String ssn,
                    String securityQuestion, String securityAnswer){
        this.firstname = firstName;
        this.lastname = lastName;
        this.address= address;
        this.zipcode = zipcode;
        this.state = state;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ssn = ssn;
        this.securityQuestion = securityQuestion;
        this.securityAnswer =  securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public ArrayList<String> getBookedFlights() {return bookedFlights;}

    public void bookFlight(String flight) {
        if (!bookedFlights.contains(flight)) {
            bookedFlights.add(flight);
            System.out.println("Flight " + flight + " successfully booked!");
        } else {
            System.out.println("You already booked this flight.");
        }
    }
        public void cancelFlight (String flight){
        if(bookedFlights.contains(flight)){
            bookedFlights.remove(flight);
            System.out.println("Flight " + flight + " successfully cancelled.");
        }
        else {
            System.out.println("You have not booked this flight yet.");
        }
    }
    public void displayBookedFlight(){
        if (bookedFlights.isEmpty()) {
            System.out.println("You have no currently booked.");
        }
        else {
            System.out.println("Flights booked by " + username + ": " + bookedFlights);
        }
    }
    @Override
    public String toString() {
        return "Customer: " + firstname + " " + lastname + " | Username: " + username + " | Email: " + email;
    }


}
