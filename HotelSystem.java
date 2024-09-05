import java.util.*;

class Accommodation {
    private int roomNumber;
    private String roomType;
    private boolean wifiIncluded;
    private boolean isBooked;
    private double roomRate;

    public Accommodation(int number, String type, boolean wifi, double rate) {
        roomNumber = number;
        roomType = type;
        wifiIncluded = wifi;
        roomRate = rate;
        isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean hasWifi() {
        return wifiIncluded;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public double getRoomRate() {
        return roomRate;
    }

    public void book() {
        isBooked = true;
    }

    public void unbook() {
        isBooked = false;
    }
}

class Visitor {
    private String name;
    private String contact;
    private int roomNumber;
    private int daysStayed;

    public Visitor(String name, String contact, int roomNumber, int daysStayed) {
        this.name = name;
        this.contact = contact;
        this.roomNumber = roomNumber;
        this.daysStayed = daysStayed;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getDaysStayed() {
        return daysStayed;
    }
}

class HotelManager {
    private List<Accommodation> accommodations;
    private Map<String, Visitor> reservations;

    public HotelManager() {
        accommodations = new ArrayList<>();
        reservations = new HashMap<>();
    }

    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
    }

    public void viewAvailability() {
        for (Accommodation room : accommodations) {
            System.out.println("Room " + room.getRoomNumber() + ": " + (room.isBooked() ? "Booked" : "Available"));
        }
    }

    public boolean makeReservation(String name, String contact, String roomType, int daysStayed) {
        for (Accommodation room : accommodations) {
            if (!room.isBooked() && room.getRoomType().equalsIgnoreCase(roomType)) {
                room.book();
                int roomNumber = room.getRoomNumber();
                Visitor visitor = new Visitor(name, contact, roomNumber, daysStayed);
                reservations.put(contact, visitor);
                double roomCharge = room.getRoomRate() * daysStayed;
                System.out.println("Reservation successful. Total charge: $" + roomCharge);
                return true;
            }
        }
        System.out.println("No available rooms of type " + roomType);
        return false;
    }

    public boolean cancelReservation(String contact) {
        if (reservations.containsKey(contact)) {
            Visitor visitor = reservations.get(contact);
            int roomNumber = visitor.getRoomNumber();
            for (Accommodation room : accommodations) {
                if (room.getRoomNumber() == roomNumber) {
                    room.unbook();
                    reservations.remove(contact);
                    System.out.println("Reservation for " + visitor.getName() + " canceled.");
                    return true;
                }
            }
        }
        System.out.println("Reservation not found for contact: " + contact);
        return false;
    }
}

public class HotelSystem {
    public static void main(String[] args) {
        Accommodation room1 = new Accommodation(101, "Deluxe Suite", true, 200);
        Accommodation room2 = new Accommodation(102, "Standard Room", true, 150);
        Accommodation room3 = new Accommodation(103, "Standard Room", true, 150);
        Accommodation room4 = new Accommodation(201, "Luxury Suite", false, 300);
        Accommodation room5 = new Accommodation(202, "Deluxe Suite", false, 250);

        HotelManager manager = new HotelManager();
        manager.addAccommodation(room1);
        manager.addAccommodation(room2);
        manager.addAccommodation(room3);
        manager.addAccommodation(room4);
        manager.addAccommodation(room5);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Management System Menu:");
            System.out.println("1. View Room Availability");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manager.viewAvailability();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your contact: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter room type: ");
                    String roomType = scanner.nextLine();
                    System.out.print("Enter number of days: ");
                    int daysStayed = scanner.nextInt();
                    manager.makeReservation(name, contact, roomType, daysStayed);
                    break;
                case 3:
                    System.out.print("Enter your contact to cancel the reservation: ");
                    String cancelContact = scanner.nextLine();
                    manager.cancelReservation(cancelContact);
                    break;
                case 4:
                    System.out.println("Exiting Hotel Management System. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
