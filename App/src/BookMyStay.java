// Version 11.0 - Concurrent Booking Simulation (Thread Safety)

import java.util.*;

// Room Model
class Room {
    private String type;

    public Room(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

// Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // SYNCHRONIZED → ensures thread safety
    public synchronized boolean bookRoom(String type) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Booking Task (Thread)
class BookingTask implements Runnable {

    private RoomInventory inventory;
    private String roomType;
    private String userName;

    public BookingTask(RoomInventory inventory, String roomType, String userName) {
        this.inventory = inventory;
        this.roomType = roomType;
        this.userName = userName;
    }

    @Override
    public void run() {

        System.out.println(userName + " trying to book " + roomType);

        boolean success = inventory.bookRoom(roomType);

        if (success) {
            System.out.println("Booking SUCCESS for " + userName);
        } else {
            System.out.println("Booking FAILED for " + userName);
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        // Setup inventory with only 1 room
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Suite", 1);

        // Multiple users (threads)
        Thread t1 = new Thread(new BookingTask(inventory, "Suite", "User1"));
        Thread t2 = new Thread(new BookingTask(inventory, "Suite", "User2"));
        Thread t3 = new Thread(new BookingTask(inventory, "Suite", "User3"));

        // Start threads simultaneously
        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        // Final state
        inventory.displayInventory();
    }
}