// Version 4.0 - Room Search & Availability (Read-Only Access)

import java.util.*;

// Domain Model: Room
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

// Inventory Class (Centralized State)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // READ-ONLY
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    // READ-ONLY
    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service (READ-ONLY)
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory, Map<String, Room> roomData) {

        System.out.println("\n--- Available Rooms ---");

        for (String type : inventory.getAllRoomTypes()) {

            int available = inventory.getAvailability(type);

            // Defensive check: show only available rooms
            if (available > 0 && roomData.containsKey(type)) {

                Room room = roomData.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

// Main Class (must match file name)
public class BookMyStay {

    public static void main(String[] args) {

        // Step 1: Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);   // Not available
        inventory.addRoomType("Suite", 2);

        // Step 2: Room Details (Domain Data)
        Map<String, Room> roomData = new HashMap<>();
        roomData.put("Single", new Room("Single", 1000, "WiFi, AC"));
        roomData.put("Double", new Room("Double", 2000, "WiFi, AC, TV"));
        roomData.put("Suite", new Room("Suite", 5000, "WiFi, AC, TV, Jacuzzi"));

        // Step 3: Perform Search (READ-ONLY)
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(inventory, roomData);

        // NOTE: Inventory is NOT modified anywhere ✅
    }
}