import java.io.*;
import java.util.*;

public class AirlineReservationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Flight> flights = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final User admin = new User("admin", "admin");

    public static void main(String[] args) {
        loadData(); // Load data from files when the application starts
        mainMenu();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("Welcome to the Airline Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. User Register");
            System.out.println("4. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    userRegister();
                    break;
                case 4:
                    saveData(); // Save data to files before exiting
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials. Please try again.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. View Flights");
            System.out.println("2. Add Flight");
            System.out.println("3. Remove Flight");
            System.out.println("4. Logout");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewFlights();
                    break;
                case 2:
                    addFlight();
                    break;
                case 3:
                    removeFlight();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            userMenu(user);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void userRegister() {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();
        if (findUserByUsername(username) != null) {
            System.out.println("Username already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        System.out.println("User registered successfully.");
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("User Menu");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Reservations");
            System.out.println("4. Logout");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchFlights();
                    break;
                case 2:
                    bookFlight(user);
                    break;
                case 3:
                    viewReservations(user);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    private static void addFlight() {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter available seats: ");
        int availableSeats = scanner.nextInt();
        scanner.nextLine();

        flights.add(new Flight(flightNumber, origin, destination, date, availableSeats));
        System.out.println("Flight added successfully.");
    }

    private static void removeFlight() {
        System.out.print("Enter flight number to remove: ");
        String flightNumber = scanner.nextLine();

        Flight flightToRemove = null;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flightToRemove = flight;
                break;
            }
        }

        if (flightToRemove != null) {
            flights.remove(flightToRemove);
            System.out.println("Flight removed successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }

    private static void searchFlights() {
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();

        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && flight.getDestination().equalsIgnoreCase(destination)) {
                System.out.println(flight);
            }
        }
    }

    private static void bookFlight(User user) {
        System.out.print("Enter flight number to book: ");
        String flightNumber = scanner.nextLine();

        Flight flightToBook = findFlightByNumber(flightNumber);
        if (flightToBook != null) {
            if (flightToBook.getAvailableSeats() > 0) {
                flightToBook.setAvailableSeats(flightToBook.getAvailableSeats() - 1);
                reservations.add(new Reservation(UUID.randomUUID().toString(), flightToBook, user));
                System.out.println("Flight booked successfully.");
            } else {
                System.out.println("No available seats on this flight.");
            }
        } else {
            System.out.println("Flight not found.");
        }
    }

    private static void viewReservations(User user) {
        System.out.println("Your Reservations:");
        for (Reservation reservation : reservations) {
            if (reservation.getUser().getUsername().equals(user.getUsername())) {
                System.out.println(reservation);
            }
        }
    }

    private static void loadData() {
        loadUsers();
        loadFlights();
        loadReservations();
    }

    private static void saveData() {
        saveUsers();
        saveFlights();
        saveReservations();
    }

    private static void loadUsers() {
        try {
            File file = new File("users.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split(",");
                users.add(new User(userData[0], userData[1]));
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading users data: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("users.txt"));
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPassword());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving users data: " + e.getMessage());
        }
    }

    private static void loadFlights() {
        try {
            File file = new File("flights.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] flightData = fileScanner.nextLine().split(",");
                flights.add(new Flight(flightData[0], flightData[1], flightData[2], flightData[3], Integer.parseInt(flightData[4])));
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading flights data: " + e.getMessage());
        }
    }

    private static void saveFlights() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("flights.txt"));
            for (Flight flight : flights) {
                writer.println(flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + flight.getDate() + "," + flight.getAvailableSeats());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving flights data: " + e.getMessage());
        }
    }

    private static void loadReservations() {
        try {
            File file = new File("reservations.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] reservationData = fileScanner.nextLine().split(",");
                Flight flight = findFlightByNumber(reservationData[1]);
                User user = findUserByUsername(reservationData[2]);
                reservations.add(new Reservation(reservationData[0], flight, user));
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading reservations data: " + e.getMessage());
        }
    }

    private static void saveReservations() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("reservations.txt"));
            for (Reservation reservation : reservations) {
                writer.println(reservation.getReservationId() + "," + reservation.getFlight().getFlightNumber() + "," + reservation.getUser().getUsername());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving reservations data: " + e.getMessage());
        }
    }

    private static Flight findFlightByNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
