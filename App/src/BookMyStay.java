// Version 7.0 - Add-On Service Selection

import java.util.*;

// Domain Model: Room
class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

// Add-On Service Model
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Reservation Entity
class Reservation {
    private static int counter = 1;

    private int reservationId;
    private Room room;
    private List<AddOnService> services;
    private double totalCost;

    public Reservation(Room room) {
        this.reservationId = counter++;
        this.room = room;
        this.services = new ArrayList<>();
        this.totalCost = room.getPrice();
    }

    public int getReservationId() {
        return reservationId;
    }

    public void addService(AddOnService service) {
        services.add(service);
        totalCost += service.getCost();
    }

    public void displayDetails() {
        System.out.println("\n--- Reservation Details ---");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Type: " + room.getType());
        System.out.println("Base Price: " + room.getPrice());

        System.out.println("Add-On Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " : " + s.getCost());
        }

        System.out.println("Total Cost: " + totalCost);
    }
}

// Inventory
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        int current = getAvailability(type);
        if (current > 0) {
            inventory.put(type, current - 1);
        }
    }
}

// Reservation Service
class ReservationService {

    public Reservation bookRoom(RoomInventory inventory, Room room) {

        int available = inventory.getAvailability(room.getType());

        if (available > 0) {
            inventory.reduceAvailability(room.getType());

            Reservation reservation = new Reservation(room);

            System.out.println("\nBooking CONFIRMED!");
            System.out.println("Reservation ID: " + reservation.getReservationId());

            return reservation;
        } else {
            System.out.println("\nBooking FAILED! No rooms available.");
            return null;
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        // Step 1: Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Suite", 1);

        // Step 2: Room
        Room suite = new Room("Suite", 5000);

        // Step 3: Booking
        ReservationService service = new ReservationService();
        Reservation reservation = service.bookRoom(inventory, suite);

        if (reservation != null) {

            // Step 4: Add-On Services
            AddOnService wifi = new AddOnService("WiFi", 500);
            AddOnService breakfast = new AddOnService("Breakfast", 800);
            AddOnService spa = new AddOnService("Spa", 1500);

            reservation.addService(wifi);
            reservation.addService(breakfast);
            reservation.addService(spa);

            // Step 5: Display final bill
            reservation.displayDetails();
        }
    }
}