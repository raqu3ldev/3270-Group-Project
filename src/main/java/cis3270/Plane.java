package cis3270;

public class Plane {
    private final String model;
    private final String planeID;
    private final int capacity;
    private int currentPassengers;

    public Plane(String planeID, String model, int capacity, int currentPassengers){
        this.planeID = planeID;
        this.model = model;
        this.capacity = capacity;
        this.currentPassengers = currentPassengers;
    }

    public boolean isFull() {
        return currentPassengers >= capacity;
    }

    public void addPassenger(){
        if(!isFull()){
            currentPassengers++;
            System.out.println("Passenger successfully added.");
        }
        else {
            System.out.println("Plane is full.");
        }
    }

    public void removePassenger(){
        if(currentPassengers > 0){
            currentPassengers--;
            System.out.println("Passenger successfully removed.");
        }
        else {
            System.out.println("No current passengers.");
        }
    }
    public String getPlaneID(){
        return planeID;
    }
    public String getModel(){
        return model;
    }
    public int getCapacity(){
        return capacity;
    }
    public int getCurrentPassengers(){
        return currentPassengers;
    }
    public void displayPlaneInfo(){
        System.out.println("Plane ID: " + planeID + ", Model: " + model +
                ", Capacity: " + capacity + ", Booked: " + currentPassengers);
    }
    public void clearPlane(){
        currentPassengers = 0;
        System.out.println("All passengers removed. Plane is empty.");
    }
    @Override
    public String toString() {
        return "Plane ID: " + planeID + ", Model: " + model +
                ", Capacity: " + capacity + ", Booked: " + currentPassengers;
    }

}
