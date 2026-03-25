// Version 3.1 - Centralized Room Inventory using HashMap

import java.util.HashMap;
import java.util.Map;

// Actor Class: RoomInventory
class RoomInventory {

    // Centralized storage
    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register room types
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n--- Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class (must match file name)
public class BookMyStay {

    public static void main(String[] args) {

        // Step 1: Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Step 2: Add room types
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        // Step 3: Display inventory
        inventory.displayInventory();

        // Step 4: Check availability
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single"));

        // Step 5: Update availability
        inventory.updateAvailability("Double", 3);

        // Step 6: Display updated inventory
        inventory.displayInventory();
    }
}