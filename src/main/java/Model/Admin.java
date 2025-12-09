package Model;

public class Admin extends User {

    // extra field to show Admin has something more than a normal User
    private String department;

    // no-arg constructor (required by some tools / frameworks)
    public Admin() {
        // you could default the role here if you want
        setRole("ADMIN");
    }

    // Full constructor mirroring your big User constructor, but forcing role = "ADMIN"
    public Admin(int userId, String firstName, String lastName,
                 String address, String city, String state, String zipCode,
                 String email, String username, String password,
                 String ssn, String securityQuestion, String securityAnswer) {

        super(userId, firstName, lastName,
                address, city, state, zipCode,
                email, username, password,
                ssn, securityQuestion, securityAnswer,
                "ADMIN"); // hard-code role for admins
    }

    // Smaller constructor (like your second User constructor), again forcing ADMIN
    public Admin(int userId, String firstName, String lastName,
                 String username) {

        super(userId, firstName, lastName, username, "ADMIN");
    }

    // admin-specific field
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // override toString to demonstrate polymorphism
    @Override
    public String toString() {
        return "[ADMIN] " + super.toString();
    }
}

