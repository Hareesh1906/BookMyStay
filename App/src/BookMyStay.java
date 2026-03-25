// Version 8.0 - Booking History & Reporting

import java.util.*;

// Room Model
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

// Add-On Service
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

    public Room getRoom() {
        return room;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void addService(AddOnService service) {
        services.add(service);
        totalCost += service.getCost();
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Room: " + room.getType() +
                ", Total Cost: " + totalCost);
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

// Booking History (NEW)
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public void showAllBookings() {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : history) {
            r.display();
        }
    }

    // Reporting: Total Revenue
    public void generateReport() {
        double totalRevenue = 0;

        for (Reservation r : history) {
            totalRevenue += r.getTotalCost();
        }

        System.out.println("\n--- Report ---");
        System.out.println("Total Bookings: " + history.size());
        System.out.println("Total Revenue: " + totalRevenue);
    }
}

// Reservation Service
class ReservationService {

    private BookingHistory history;

    public ReservationService(BookingHistory history) {
        this.history = history;
    }

    public Reservation bookRoom(RoomInventory inventory, Room room) {

        int available = inventory.getAvailability(room.getType());

        if (available > 0) {
            inventory.reduceAvailability(room.getType());

            Reservation reservation = new Reservation(room);

            // Save to history
            history.addReservation(reservation);

            System.out.println("Booking CONFIRMED! ID: " + reservation.getReservationId());

            return reservation;
        } else {
            System.out.println("Booking FAILED!");
            return null;
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        // Setup
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);

        Room single = new Room("Single", 1000);

        BookingHistory history = new BookingHistory();
        ReservationService service = new ReservationService(history);

        // Booking 1
        Reservation r1 = service.bookRoom(inventory, single);
        if (r1 != null) {
            r1.addService(new AddOnService("WiFi", 200));
        }

        // Booking 2
        Reservation r2 = service.bookRoom(inventory, single);
        if (r2 != null) {
            r2.addService(new AddOnService("Breakfast", 300));
        }

        // Show history
        history.showAllBookings();

        // Generate report
        history.generateReport();
    }
}