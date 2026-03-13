import java.util.Collection;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Date: March 12, 2026
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public class TicketMinerSystem {

    private UserManager userManager;
    private EventManager eventManager;
    private VenueManager venueManager;
    private Logger logger;
    private Scanner myScanner;

    /**
     * Main constructor for the TicketMiner system.
     * Initializes managers, logger, scanner, loads data, and seeds a default admin.
     */
    public TicketMinerSystem() {
        userManager = new UserManager();
        eventManager = new EventManager();
        venueManager = new VenueManager();
        logger = new Logger("ticketminer_log.txt");
        myScanner = new Scanner(System.in);

        loadAllData();
        seedDefaultAdmin();
    }

    /**
     * Loads all initial data from CSV files.
     */
    private void loadAllData() {
        loadUsersFromCSV("Customer_List_PA1.csv");
        loadEventsFromCSV("Event_List_PA1.csv");
        loadVenuesFromCSV("Venue_List_PA1.csv");

        logger.log("Initial data loaded. Users=" + userManager.size()
                + ", Events=" + eventManager.size()
                + ", Venues=" + venueManager.size());
    }

    /**
     * Loads customers from CSV file.
     *
     * @param fileName customer CSV file name
     */
private void loadUsersFromCSV(String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0].trim());
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            String username = parts[3].trim();
            String password = parts[4].trim();
            String userType = parts[5].trim();

            User user = null;

            if (userType.equalsIgnoreCase("Customer")) {
                double moneyAvailable = 0.0;
                boolean membership = false;

                if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                    moneyAvailable = Double.parseDouble(parts[6].trim());
                }

                if (parts.length > 7 && !parts[7].trim().isEmpty()) {
                    membership = Boolean.parseBoolean(parts[7].trim());
                }

                user = new Customer(id, firstName, lastName, username, password, moneyAvailable, membership);

            } else if (userType.equalsIgnoreCase("Organizer")) {
                user = new Organizer(id, firstName, lastName, username, password);

            } else if (userType.equalsIgnoreCase("Admin")) {
                user = new Admin(id, firstName, lastName, username, password);
            }

            if (user != null) {
                userManager.addUser(user);
            }
        }

        logger.log("Users loaded successfully from " + fileName);

    } catch (IOException | NumberFormatException e) {
        System.out.println("Error loading users from CSV: " + e.getMessage());
        logger.log("Error loading users from " + fileName + ": " + e.getMessage());
    }
}

    /**
     * Loads events from CSV file.
     *
     * @param fileName event CSV file name
     */
    private void loadEventsFromCSV(String fileName) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                String name = parts[2].trim();
                LocalDate date = LocalDate.parse(parts[3].trim(), dateFormatter);
                LocalTime time = LocalTime.parse(parts[4].trim(), timeFormatter);
                double vipPrice = Double.parseDouble(parts[5].trim());
                double goldPrice = Double.parseDouble(parts[6].trim());
                double silverPrice = Double.parseDouble(parts[7].trim());

                Event event = null;

                if (type.equalsIgnoreCase("Concert")) {
                    event = new Concert(id, name, date, time, vipPrice, goldPrice, silverPrice);
                } else if (type.equalsIgnoreCase("Sport")) {
                    event = new Sport(id, name, date, time, vipPrice, goldPrice, silverPrice);
                } else if (type.equalsIgnoreCase("Special")) {
                    event = new Special(id, name, date, time, vipPrice, goldPrice, silverPrice);
                }

                if (event != null) {
                    eventManager.addEvent(event);
                }
            }

            logger.log("Events loaded successfully from " + fileName);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading events from CSV: " + e.getMessage());
            logger.log("Error loading events from " + fileName + ": " + e.getMessage());
        }
    }
    

    /**
     * Loads venues from CSV file.
     *
     * @param fileName venue CSV file name
     */
 private void loadVenuesFromCSV(String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String type = parts[2].trim();
            int capacity = Integer.parseInt(parts[3].trim());
            int concertCapacity = Integer.parseInt(parts[4].trim());
            double cost = Double.parseDouble(parts[5].trim());
            double vipPercent = Double.parseDouble(parts[6].trim());

            Venue venue = null;

            if (type.equalsIgnoreCase("Arena")) {
                venue = new Arena(id, name, capacity, concertCapacity, cost, vipPercent);

            } else if (type.equalsIgnoreCase("Auditorium")) {
                venue = new Auditorium(id, name, capacity, concertCapacity, cost, vipPercent);

            } else if (type.equalsIgnoreCase("Stadium")) {
                venue = new Stadium(id, name, capacity, concertCapacity, cost, vipPercent);

            } else if (type.equalsIgnoreCase("Open Air") || type.equalsIgnoreCase("OpenAir")) {
                venue = new OpenAir(id, name, capacity, concertCapacity, cost, vipPercent);
            }

            if (venue != null) {
                venueManager.addVenue(venue);
            }
        }

        logger.log("Venues loaded successfully from " + fileName);

    } catch (IOException | NumberFormatException e) {
        System.out.println("Error loading venues from CSV: " + e.getMessage());
        logger.log("Error loading venues from " + fileName + ": " + e.getMessage());
    }
}

    /**
     * Seeds a default admin account if one does not already exist.
     */
    private void seedDefaultAdmin() {
        if (userManager.findByUsername("admin") == null) {
            Admin admin = new Admin(9999, "System", "Admin", "admin", "admin123");
            userManager.addUser(admin);
            logger.log("Default admin account created: username=admin");
        }
    }

    /**
     * Starts the system.
     */
    public void start() {
        logger.log("TicketMiner System started.");

        System.out.println("\nData loaded successfully.");
        System.out.println("Users loaded: " + userManager.size());
        System.out.println("Events loaded: " + eventManager.size());
        System.out.println("Venues loaded: " + venueManager.size());

        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                handleRegister();
            } else if (choice.equals("2")) {
                handleLogin();
            } else if (choice.equalsIgnoreCase("EXIT")) {
                handleExit();
                running = false;
            } else {
                System.out.println("Upps! Invalid choice. Please try again.");
            }
        }

        logger.log("TicketMiner System terminated.");
    }

    /**
     * Prints the main menu.
     */
    private void printMainMenu() {
        System.out.println("\n========== TicketMiner Main Menu ==========");
        System.out.println("\n1) Register");
        System.out.println("2) Login");
        System.out.println("Type EXIT to save and quit");
        System.out.println("\n==========================================");
        System.out.print("\nSelect an option: ");
    }

    /**
     * Handles registration flow.
     */
    private void handleRegister() {
        System.out.println("\n---------- Register ----------");
        System.out.println("\n1) Register as Customer");
        System.out.println("2) Register as Organizer");
        System.out.print("\nSelect an option: ");

        String choice = myScanner.nextLine().trim();

        if (choice.equals("1")) {
            registerCustomer();
        } else if (choice.equals("2")) {
            registerOrganizer();
        } else {
            System.out.println("\nUpps! Invalid register option.");
        }
    }

    /**
     * Registers a customer.
     */
    private void registerCustomer() {
        System.out.println("\n----------Register Customer----------");
        int id = generateUserId();

        System.out.print("\nFirst name: ");
        String firstName = myScanner.nextLine().trim();

        System.out.print("\nLast name: ");
        String lastName = myScanner.nextLine().trim();

        String username = promptUniqueUsername();

        System.out.print("\nPassword: ");
        String password = myScanner.nextLine().trim();

        System.out.print("\nMoney available: ");
        double money = readDoubleSafe();

        System.out.print("\nTicketMiner membership (true/false): ");
        boolean membership = readBooleanSafe();

        Customer customer = new Customer(id, firstName, lastName, username, password, money, membership);
        boolean added = userManager.addUser(customer);

        if (added) {
            System.out.println("\nCustomer registered successfully. ID = " + id);
            logger.log("Registered Customer with ID " + id + " and username " + username);
        } else {
            System.out.println("\nUpps! Registration failed (username already exists).");
            logger.log("Failed Customer registration attempt for username " + username);
        }
    }

    /**
     * Registers an organizer.
     */
    private void registerOrganizer() {
        System.out.println("\n---------- Register Organizer ----------");
        int id = generateUserId();

        System.out.print("\nFirst name: ");
        String firstName = myScanner.nextLine().trim();

        System.out.print("\nLast name: ");
        String lastName = myScanner.nextLine().trim();

        String username = promptUniqueUsername();

        System.out.print("\nPassword: ");
        String password = myScanner.nextLine().trim();

        Organizer organizer = new Organizer(id, firstName, lastName, username, password);
        boolean added = userManager.addUser(organizer);

        if (added) {
            System.out.println("\nOrganizer registered successfully. ID = " + id);
            logger.log("Registered Organizer with ID " + id + " and username " + username);
        } else {
            System.out.println("\nUpps! Registration failed (username already exists).");
            logger.log("Failed Organizer registration attempt for username " + username);
        }
    }

    /**
     * Handles login flow.
     */
    private void handleLogin() {
        System.out.println("\n---------- Login ----------");

        System.out.print("\nUsername: ");
        String username = myScanner.nextLine().trim();

        System.out.print("\nPassword: ");
        String password = myScanner.nextLine().trim();

        User user = userManager.findByUsername(username);

        if (user == null) {
            System.out.println("\nUpps! User not found.");
            logger.log("Login failed: user not found for username " + username);
            return;
        }

        if (!user.checkPassword(password)) {
            System.out.println("\nUpps! Incorrect password.");
            logger.log("Login failed: incorrect password for username " + username);
            return;
        }

        System.out.println("\nLogin successful. Welcome, " + user.getUsername() + "!");
        logger.log("Login successful for username " + username);

        if (user instanceof Admin) {
            showAdminMenu();
        } else if (user instanceof Organizer) {
            System.out.println("[Organizer menu placeholder]");
        } else if (user instanceof Customer) {
            System.out.println("[Customer menu placeholder]");
        }
    }

    /**
     * Handles exit flow.
     */
private void handleExit() {
    saveUsersToCSV("Updated_Customer_List_PA1.csv");
    saveEventsToCSV("Updated_Event_List_PA1.csv");
    saveVenuesToCSV("Updated_Venue_List_PA1.csv");

    System.out.println("\nUpdated CSV files saved successfully.");
    logger.log("EXIT selected. Updated CSV files were saved successfully.");
}

    /**
     * Generates a simple user ID.
     *
     * @return new user ID
     */
    private int generateUserId() {
    return userManager.getNextId();
}

    /**
     * Generates a simple venue ID.
     *
     * @return new venue ID
     */
    private int generateVenueId() {
    return venueManager.getNextId();
}

    /**
     * Generates a simple event ID.
     *
     * @return new event ID
     */
private int generateEventId() {
    return eventManager.getNextId();
}

    /**
     * Prompts until a unique username is entered.
     *
     * @return unique username
     */
    private String promptUniqueUsername() {
        while (true) {
            System.out.print("\nUsername: ");
            String username = myScanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("\nWait! Username cannot be empty.");
                continue;
            }

            if (!userManager.isUsernameUnique(username)) {
                System.out.println("\nWait! Username already exists. Enter a different username.");
                continue;
            }

            return username;
        }
    }

    /**
     * Reads a double safely.
     *
     * @return valid double
     */
    private double readDoubleSafe() {
        while (true) {
            String input = myScanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("\nUpps! Invalid number. Try again: ");
            }
        }
    }

    /**
     * Reads a boolean safely.
     *
     * @return valid boolean
     */
    private boolean readBooleanSafe() {
        while (true) {
            String input = myScanner.nextLine().trim().toLowerCase();

            if (input.equals("true") || input.equals("t") || input.equals("yes") || input.equals("y")) {
                return true;
            }
            if (input.equals("false") || input.equals("f") || input.equals("no") || input.equals("n")) {
                return false;
            }

            System.out.print("\nUpps! Invalid input. Enter true/false: ");
        }
    }

    /**
     * Reads an integer safely.
     *
     * @return valid integer
     */
    private int readIntSafe() {
        while (true) {
            String input = myScanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid integer. Try again: ");
            }
        }
    }

    /**
     * Reads a positive integer safely.
     *
     * @return positive integer
     */
    private int readPositiveIntSafe() {
        while (true) {
            int value = readIntSafe();
            if (value > 0) {
                return value;
            }
            System.out.print("\nValue must be greater than 0. Try again: ");
        }
    }

    /**
     * Reads a date safely using M/d/yyyy format.
     *
     * @return valid LocalDate
     */
    private LocalDate readDateSafe() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        while (true) {
            String input = myScanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.print("\nInvalid date. Use M/d/yyyy format: ");
            }
        }
    }

    /**
     * Reads a time safely using h:mm a format.
     *
     * @return valid LocalTime
     */
    private LocalTime readTimeSafe() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", java.util.Locale.ENGLISH);

        while (true) {
            String input = myScanner.nextLine().trim();
            try {
                return LocalTime.parse(input, formatter);
            } catch (Exception e) {
                System.out.print("\nInvalid time. Use h:mm AM/PM format: ");
            }
        }
    }

    /**
     * Shows the admin menu.
     */
    private void showAdminMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1) Manage Users");
            System.out.println("2) Manage Venues");
            System.out.println("3) Manage Events");
            System.out.println("4) Logout");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                showManageUsersMenu();
            } else if (choice.equals("2")) {
                showManageVenuesMenu();
            } else if (choice.equals("3")) {
                showManageEventsMenu();
            } else if (choice.equals("4")) {
                System.out.println("\nLogging out...");
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Shows manage users menu.
     */
    private void showManageUsersMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== MANAGE USERS ==========");
            System.out.println("1) Add");
            System.out.println("2) View");
            System.out.println("3) Update");
            System.out.println("4) Delete");
            System.out.println("5) Return to Admin Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminAddUser();
            } else if (choice.equals("2")) {
                showViewUsersMenu();
            } else if (choice.equals("3")) {
                adminUpdateUser();
            } else if (choice.equals("4")) {
                adminDeleteUser();
            } else if (choice.equals("5")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Adds a user as admin.
     */
    private void adminAddUser() {
        System.out.println("\n========== ADD USER ==========");
        System.out.println("1) Add Customer");
        System.out.println("2) Add Organizer");
        System.out.println("3) Add Admin");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        int id = generateUserId();

        System.out.print("\nFirst name: ");
        String firstName = myScanner.nextLine().trim();

        System.out.print("\nLast name: ");
        String lastName = myScanner.nextLine().trim();

        String username = promptUniqueUsername();

        System.out.print("\nPassword: ");
        String password = myScanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("\nMoney available: ");
            double money = readDoubleSafe();

            System.out.print("\nTicketMiner membership (true/false): ");
            boolean membership = readBooleanSafe();

            Customer customer = new Customer(id, firstName, lastName, username, password, money, membership);

            if (userManager.addUser(customer)) {
                System.out.println("\nCustomer added successfully.");
                logger.log("Admin added Customer with username " + username);
            } else {
                System.out.println("\nFailed to add customer.");
            }
        } else if (choice.equals("2")) {
            Organizer organizer = new Organizer(id, firstName, lastName, username, password);

            if (userManager.addUser(organizer)) {
                System.out.println("\nOrganizer added successfully.");
                logger.log("Admin added Organizer with username " + username);
            } else {
                System.out.println("\nFailed to add organizer.");
            }
        } else if (choice.equals("3")) {
            Admin admin = new Admin(id, firstName, lastName, username, password);

            if (userManager.addUser(admin)) {
                System.out.println("\nAdmin added successfully.");
                logger.log("Admin added Admin with username " + username);
            } else {
                System.out.println("\nFailed to add admin.");
            }
        } else {
            System.out.println("\nInvalid option.");
        }
    }

    /**
     * Shows view users menu.
     */
    private void showViewUsersMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== VIEW USERS ==========");
            System.out.println("1) Display All Members");
            System.out.println("2) Search for a Member");
            System.out.println("3) Return to Manage Users Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminViewAllUsers();
            } else if (choice.equals("2")) {
                adminSearchUser();
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Searches for a user as admin.
     */
    private void adminSearchUser() {
        System.out.println("\n========== SEARCH USER ==========");
        System.out.println("Search user by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Username");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        User user = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter user ID: ");
            int id = readIntSafe();
            user = userManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();
            user = userManager.findByUsername(username);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (user == null) {
            System.out.println("\nMember not found.");
            return;
        }

        System.out.println("\n========== MEMBER FOUND ==========");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Type: " + user.getClass().getSimpleName());

        if (user instanceof Customer) {
            System.out.println("Category: Customer");
        } else if (user instanceof Organizer) {
            System.out.println("Category: Organizer");
        } else if (user instanceof Admin) {
            System.out.println("Category: Admin");
        }
    }

    /**
     * Displays all users.
     */
    private void adminViewAllUsers() {
        System.out.println("\n========== ALL MEMBERS ==========");

        Collection<User> users = userManager.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("\nNo members found in the system.");
            return;
        }

        for (User user : users) {
            System.out.println("\n---------------------------------");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Type: " + user.getClass().getSimpleName());

            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.println("Balance: " + customer.getMoneyAvailable());
                System.out.println("Membership: " + customer.getMembership());
            } else if (user instanceof Organizer) {
                System.out.println("Role: Organizer");
            } else if (user instanceof Admin) {
                System.out.println("Role: Admin");
            }
        }

        System.out.println("\n========== END OF LIST ==========");
    }

    /**
     * Deletes a user as admin.
     */
    private void adminDeleteUser() {
        System.out.println("\n========== DELETE USER ==========");
        System.out.println("Search user by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Username");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        User user = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter user ID: ");
            int id = readIntSafe();
            user = userManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();
            user = userManager.findByUsername(username);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (user == null) {
            System.out.println("\nMember not found.");
            return;
        }

        System.out.println("\n========== MEMBER FOUND ==========");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Type: " + user.getClass().getSimpleName());

        System.out.print("\nAre you sure you want to delete this member? (yes/no): ");
        String confirm = myScanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            boolean deleted = userManager.deleteUser(user);

            if (deleted) {
                System.out.println("\nMember deleted successfully.");
                logger.log("Admin deleted user with ID " + user.getId()
                        + " and username " + user.getUsername());
            } else {
                System.out.println("\nFailed to delete member.");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }

    /**
     * Updates a user as admin.
     */
    private void adminUpdateUser() {
        System.out.println("\n========== UPDATE USER ==========");
        System.out.println("Search member by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Username");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        User user = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter user ID: ");
            int id = readIntSafe();
            user = userManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();
            user = userManager.findByUsername(username);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (user == null) {
            System.out.println("\nMember not found.");
            return;
        }

        System.out.println("\n========== MEMBER FOUND ==========");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Type: " + user.getClass().getSimpleName());

        System.out.println("\nWhat would you like to update?");
        System.out.println("1) Change Name");
        System.out.println("2) Change Username");
        System.out.println("3) Change Password");

        System.out.print("\nSelect an option: ");
        String updateChoice = myScanner.nextLine().trim();

        if (updateChoice.equals("1")) {
            System.out.print("\nEnter new first name: ");
            String newFirstName = myScanner.nextLine().trim();

            System.out.print("\nEnter new last name: ");
            String newLastName = myScanner.nextLine().trim();

            user.setFirstName(newFirstName);
            user.setLastName(newLastName);

            System.out.println("\nMember name updated successfully.");
            logger.log("Admin updated name for user ID " + user.getId());

        } else if (updateChoice.equals("2")) {
            while (true) {
                System.out.print("\nEnter new username: ");
                String newUsername = myScanner.nextLine().trim();

                if (newUsername.isEmpty()) {
                    System.out.println("\nUsername cannot be empty.");
                    continue;
                }

                if (newUsername.equalsIgnoreCase(user.getUsername())) {
                    System.out.println("\nThe new username must be different from the current username.");
                    continue;
                }

                boolean updated = userManager.updateUsername(user, newUsername);

                if (updated) {
                    System.out.println("\nUsername updated successfully.");
                    logger.log("Admin updated username for user ID " + user.getId()
                            + " to " + newUsername);
                    break;
                } else {
                    System.out.println("\nUsername already exists. Enter a different username.");
                }
            }

        } else if (updateChoice.equals("3")) {
            System.out.print("\nEnter new password: ");
            String newPassword = myScanner.nextLine().trim();

            user.setPassword(newPassword);

            System.out.println("\nPassword updated successfully.");
            logger.log("Admin updated password for user ID " + user.getId());

        } else {
            System.out.println("\nInvalid option.");
        }
    }

    /**
     * Shows manage venues menu.
     */
    private void showManageVenuesMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== MANAGE VENUES ==========");
            System.out.println("1) Add");
            System.out.println("2) View");
            System.out.println("3) Update");
            System.out.println("4) Delete");
            System.out.println("5) Return to Admin Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminAddVenue();
            } else if (choice.equals("2")) {
                showViewVenuesMenu();
            } else if (choice.equals("3")) {
                adminUpdateVenue();
            } else if (choice.equals("4")) {
                adminDeleteVenue();
            } else if (choice.equals("5")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Adds a venue as admin.
     */
    private void adminAddVenue() {
        System.out.println("\n========== ADD VENUE ==========");
        System.out.println("1) Add Arena");
        System.out.println("2) Add Auditorium");
        System.out.println("3) Add Stadium");
        System.out.println("4) Add OpenAir");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        int id = generateVenueId();

        System.out.print("\nVenue name: ");
        String name = myScanner.nextLine().trim();

        System.out.print("\nCapacity: ");
        int capacity = readPositiveIntSafe();

        System.out.print("\nConcert capacity: ");
        int concertCapacity = readPositiveIntSafe();

        System.out.print("\nCost: ");
        double cost = readDoubleSafe();

        System.out.print("\nVIP percentage: ");
        double vipPercent = readDoubleSafe();

        Venue venue = null;

        if (choice.equals("1")) {
            venue = new Arena(id, name, capacity, concertCapacity, cost, vipPercent);
        } else if (choice.equals("2")) {
            venue = new Auditorium(id, name, capacity, concertCapacity, cost, vipPercent);
        } else if (choice.equals("3")) {
            venue = new Stadium(id, name, capacity, concertCapacity, cost, vipPercent);
        } else if (choice.equals("4")) {
            venue = new OpenAir(id, name, capacity, concertCapacity, cost, vipPercent);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (venueManager.addVenue(venue)) {
            System.out.println("\nVenue added successfully. ID = " + id);
            logger.log("Admin added venue ID " + id + " of type " + venue.getType());
        } else {
            System.out.println("\nFailed to add venue.");
        }
    }

    /**
     * Shows view venues menu.
     */
    private void showViewVenuesMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== VIEW VENUES ==========");
            System.out.println("1) Display All Venues");
            System.out.println("2) Search for a Venue");
            System.out.println("3) Return to Manage Venues Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminViewAllVenues();
            } else if (choice.equals("2")) {
                adminSearchVenue();
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Displays all venues.
     */
    private void adminViewAllVenues() {
        System.out.println("\n========== ALL VENUES ==========");

        Collection<Venue> venues = venueManager.getAllVenues();

        if (venues.isEmpty()) {
            System.out.println("\nNo venues found in the system.");
            return;
        }

        for (Venue venue : venues) {
            System.out.println("\n---------------------------------");
            System.out.println("ID: " + venue.getId());
            System.out.println("Name: " + venue.getName());
            System.out.println("Type: " + venue.getType());
            System.out.println("Capacity: " + venue.getCapacity());
            System.out.println("Concert Capacity: " + venue.getConcertCapacity());
            System.out.println("Cost: " + venue.getCost());
            System.out.println("VIP Percent: " + venue.getVipPercent());
        }

        System.out.println("\n========== END OF LIST ==========");
        logger.log("Admin displayed all venues.");
    }

    /**
     * Searches for a venue as admin.
     */
    private void adminSearchVenue() {
        System.out.println("\n========== SEARCH VENUE ==========");
        System.out.println("Search venue by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Type");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Venue venue = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter venue ID: ");
            int id = readIntSafe();
            venue = venueManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter venue name: ");
            String name = myScanner.nextLine().trim();
            venue = venueManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter venue type: ");
            String type = myScanner.nextLine().trim();
            venue = venueManager.findByType(type);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (venue == null) {
            System.out.println("\nVenue not found.");
            logger.log("Admin searched venue but none was found.");
            return;
        }

        System.out.println("\n========== VENUE FOUND ==========");
        System.out.println("ID: " + venue.getId());
        System.out.println("Name: " + venue.getName());
        System.out.println("Type: " + venue.getType());
        System.out.println("Capacity: " + venue.getCapacity());
        System.out.println("Concert Capacity: " + venue.getConcertCapacity());
        System.out.println("Cost: " + venue.getCost());
        System.out.println("VIP Percent: " + venue.getVipPercent());

        logger.log("Admin searched and found venue ID " + venue.getId());
    }

    /**
     * Updates a venue as admin.
     */
    private void adminUpdateVenue() {
        System.out.println("\n========== UPDATE VENUE ==========");
        System.out.println("Search venue by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Type");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Venue venue = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter venue ID: ");
            int id = readIntSafe();
            venue = venueManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter venue name: ");
            String name = myScanner.nextLine().trim();
            venue = venueManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter venue type: ");
            String type = myScanner.nextLine().trim();
            venue = venueManager.findByType(type);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (venue == null) {
            System.out.println("\nVenue not found.");
            return;
        }

        System.out.println("\n========== VENUE FOUND ==========");
        System.out.println("ID: " + venue.getId());
        System.out.println("Name: " + venue.getName());
        System.out.println("Type: " + venue.getType());

        System.out.println("\nWhat would you like to update?");
        System.out.println("1) Change Name");
        System.out.println("2) Change Cost");
        System.out.println("3) Change Capacity");

        System.out.print("\nSelect an option: ");
        String updateChoice = myScanner.nextLine().trim();

        if (updateChoice.equals("1")) {
            System.out.print("\nEnter new venue name: ");
            String newName = myScanner.nextLine().trim();
            venue.setName(newName);

            System.out.println("\nVenue name updated successfully.");
            logger.log("Admin updated name for venue ID " + venue.getId());

        } else if (updateChoice.equals("2")) {
            System.out.print("\nEnter new cost: ");
            double newCost = readDoubleSafe();
            venue.setCost(newCost);

            System.out.println("\nVenue cost updated successfully.");
            logger.log("Admin updated cost for venue ID " + venue.getId());

        } else if (updateChoice.equals("3")) {
            System.out.print("\nEnter new capacity: ");
            int newCapacity = readPositiveIntSafe();

            System.out.print("\nEnter new concert capacity: ");
            int newConcertCapacity = readPositiveIntSafe();

            venue.setCapacity(newCapacity, newConcertCapacity);

            System.out.println("\nVenue capacity updated successfully.");
            logger.log("Admin updated capacity for venue ID " + venue.getId());

        } else {
            System.out.println("\nInvalid option.");
        }
    }

    /**
     * Deletes a venue as admin.
     */
    private void adminDeleteVenue() {
        System.out.println("\n========== DELETE VENUE ==========");
        System.out.println("Search venue by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Type");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Venue venue = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter venue ID: ");
            int id = readIntSafe();
            venue = venueManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter venue name: ");
            String name = myScanner.nextLine().trim();
            venue = venueManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter venue type: ");
            String type = myScanner.nextLine().trim();
            venue = venueManager.findByType(type);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (venue == null) {
            System.out.println("\nVenue not found.");
            return;
        }

        System.out.println("\n========== VENUE FOUND ==========");
        System.out.println("ID: " + venue.getId());
        System.out.println("Name: " + venue.getName());
        System.out.println("Type: " + venue.getType());

        System.out.print("\nAre you sure you want to delete this venue? (yes/no): ");
        String confirm = myScanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            boolean deleted = venueManager.deleteVenue(venue);

            if (deleted) {
                System.out.println("\nVenue deleted successfully.");
                logger.log("Admin deleted venue ID " + venue.getId());
            } else {
                System.out.println("\nFailed to delete venue.");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }

    /**
     * Shows manage events menu.
     */
    private void showManageEventsMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== MANAGE EVENTS ==========");
            System.out.println("1) Add");
            System.out.println("2) View");
            System.out.println("3) Update");
            System.out.println("4) Delete");
            System.out.println("5) Return to Admin Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminAddEvent();
            } else if (choice.equals("2")) {
                showViewEventsMenu();
            } else if (choice.equals("3")) {
                adminUpdateEvent();
            } else if (choice.equals("4")) {
                adminDeleteEvent();
            } else if (choice.equals("5")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Adds an event as admin.
     */
    private void adminAddEvent() {
        System.out.println("\n========== ADD EVENT ==========");
        System.out.println("1) Add Concert");
        System.out.println("2) Add Sport");
        System.out.println("3) Add Special");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        int id = generateEventId();

        System.out.print("\nEvent name: ");
        String name = myScanner.nextLine().trim();

        System.out.print("\nDate (M/d/yyyy): ");
        LocalDate date = readDateSafe();

        System.out.print("\nTime (h:mm AM/PM): ");
        LocalTime time = readTimeSafe();

        System.out.print("\nVIP price: ");
        double vipPrice = readDoubleSafe();

        System.out.print("\nGold price: ");
        double goldPrice = readDoubleSafe();

        System.out.print("\nSilver price: ");
        double silverPrice = readDoubleSafe();

        Event event = null;

        if (choice.equals("1")) {
            event = new Concert(id, name, date, time, vipPrice, goldPrice, silverPrice);
        } else if (choice.equals("2")) {
            event = new Sport(id, name, date, time, vipPrice, goldPrice, silverPrice);
        } else if (choice.equals("3")) {
            event = new Special(id, name, date, time, vipPrice, goldPrice, silverPrice);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (eventManager.addEvent(event)) {
            System.out.println("\nEvent added successfully. ID = " + id);
            logger.log("Admin added event ID " + id + " of type " + event.getType());
        } else {
            System.out.println("\nFailed to add event.");
        }
    }

    /**
     * Shows view events menu.
     */
    private void showViewEventsMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========== VIEW EVENTS ==========");
            System.out.println("1) Display All Events");
            System.out.println("2) Search for an Event");
            System.out.println("3) Return to Manage Events Menu");

            System.out.print("\nSelect an option: ");
            String choice = myScanner.nextLine().trim();

            if (choice.equals("1")) {
                adminViewAllEvents();
            } else if (choice.equals("2")) {
                adminSearchEvent();
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
     * Displays all events.
     */
    private void adminViewAllEvents() {
        System.out.println("\n========== ALL EVENTS ==========");

        Collection<Event> events = eventManager.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("\nNo events found in the system.");
            return;
        }

        for (Event event : events) {
            System.out.println("\n---------------------------------");
            System.out.println("ID: " + event.getId());
            System.out.println("Name: " + event.getName());
            System.out.println("Type: " + event.getType());
            System.out.println("Date: " + event.getDate());
            System.out.println("Time: " + event.getTime());
            System.out.println("VIP Price: " + event.getVipPrice());
            System.out.println("Gold Price: " + event.getGoldPrice());
            System.out.println("Silver Price: " + event.getSilverPrice());
        }

        System.out.println("\n========== END OF LIST ==========");
        logger.log("Admin displayed all events.");
    }

    /**
     * Searches for an event as admin.
     */
    private void adminSearchEvent() {
        System.out.println("\n========== SEARCH EVENT ==========");
        System.out.println("Search event by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Date");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Event event = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter event ID: ");
            int id = readIntSafe();
            event = eventManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter event name: ");
            String name = myScanner.nextLine().trim();
            event = eventManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter event date (M/d/yyyy): ");
            LocalDate date = readDateSafe();
            event = eventManager.findByDate(date);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (event == null) {
            System.out.println("\nEvent not found.");
            logger.log("Admin searched event but none was found.");
            return;
        }

        System.out.println("\n========== EVENT FOUND ==========");
        System.out.println("ID: " + event.getId());
        System.out.println("Name: " + event.getName());
        System.out.println("Type: " + event.getType());
        System.out.println("Date: " + event.getDate());
        System.out.println("Time: " + event.getTime());
        System.out.println("VIP Price: " + event.getVipPrice());
        System.out.println("Gold Price: " + event.getGoldPrice());
        System.out.println("Silver Price: " + event.getSilverPrice());

        logger.log("Admin searched and found event ID " + event.getId());
    }

    /**
     * Updates an event as admin.
     */
    private void adminUpdateEvent() {
        System.out.println("\n========== UPDATE EVENT ==========");
        System.out.println("Search event by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Date");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Event event = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter event ID: ");
            int id = readIntSafe();
            event = eventManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter event name: ");
            String name = myScanner.nextLine().trim();
            event = eventManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter event date (M/d/yyyy): ");
            LocalDate date = readDateSafe();
            event = eventManager.findByDate(date);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (event == null) {
            System.out.println("\nEvent not found.");
            return;
        }

        System.out.println("\n========== EVENT FOUND ==========");
        System.out.println("ID: " + event.getId());
        System.out.println("Name: " + event.getName());
        System.out.println("Type: " + event.getType());

        System.out.println("\nWhat would you like to update?");
        System.out.println("1) Change Name");
        System.out.println("2) Change Date and Time");

        System.out.print("\nSelect an option: ");
        String updateChoice = myScanner.nextLine().trim();

        if (updateChoice.equals("1")) {
            System.out.print("\nEnter new event name: ");
            String newName = myScanner.nextLine().trim();
            event.setName(newName);

            System.out.println("\nEvent name updated successfully.");
            logger.log("Admin updated name for event ID " + event.getId());

        } else if (updateChoice.equals("2")) {
            System.out.print("\nEnter new date (M/d/yyyy): ");
            LocalDate newDate = readDateSafe();

            System.out.print("\nEnter new time (h:mm AM/PM): ");
            LocalTime newTime = readTimeSafe();

            event.setDate(newDate, newTime);

            System.out.println("\nEvent date and time updated successfully.");
            logger.log("Admin updated date/time for event ID " + event.getId());

        } else {
            System.out.println("\nInvalid option.");
        }
    }

    /**
     * Deletes an event as admin.
     */
    private void adminDeleteEvent() {
        System.out.println("\n========== DELETE EVENT ==========");
        System.out.println("Search event by:");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) Date");

        System.out.print("\nSelect an option: ");
        String choice = myScanner.nextLine().trim();

        Event event = null;

        if (choice.equals("1")) {
            System.out.print("\nEnter event ID: ");
            int id = readIntSafe();
            event = eventManager.findById(id);
        } else if (choice.equals("2")) {
            System.out.print("\nEnter event name: ");
            String name = myScanner.nextLine().trim();
            event = eventManager.findByName(name);
        } else if (choice.equals("3")) {
            System.out.print("\nEnter event date (M/d/yyyy): ");
            LocalDate date = readDateSafe();
            event = eventManager.findByDate(date);
        } else {
            System.out.println("\nInvalid option.");
            return;
        }

        if (event == null) {
            System.out.println("\nEvent not found.");
            return;
        }

        System.out.println("\n========== EVENT FOUND ==========");
        System.out.println("ID: " + event.getId());
        System.out.println("Name: " + event.getName());
        System.out.println("Type: " + event.getType());

        System.out.print("\nAre you sure you want to delete this event? (yes/no): ");
        String confirm = myScanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            boolean deleted = eventManager.deleteEvent(event);

            if (deleted) {
                System.out.println("\nEvent deleted successfully.");
                logger.log("Admin deleted event ID " + event.getId());
            } else {
                System.out.println("\nFailed to delete event.");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }
private void saveUsersToCSV(String fileName) {
    try (PrintWriter pw = new PrintWriter(fileName)) {

        pw.println("ID,First Name,Last Name,Username,Password,User Type,Money Available,TicketMiner Membership");

        Collection<User> users = userManager.getAllUsers();

        for (User user : users) {

            String userType = user.getClass().getSimpleName();

            if (user instanceof Customer) {
                Customer customer = (Customer) user;

                pw.println(
                    user.getId() + ","
                    + user.getFirstName() + ","
                    + user.getLastName() + ","
                    + user.getUsername() + ","
                    + user.getPassword() + ","
                    + userType + ","
                    + customer.getMoneyAvailable() + ","
                    + customer.getMembership()
                );

            } else {

                pw.println(
                    user.getId() + ","
                    + user.getFirstName() + ","
                    + user.getLastName() + ","
                    + user.getUsername() + ","
                    + user.getPassword() + ","
                    + userType + ",,"
                );
            }
        }

        logger.log("User CSV saved successfully to " + fileName);

    } catch (IOException e) {
        System.out.println("Error saving users CSV: " + e.getMessage());
        logger.log("Error saving users CSV to " + fileName + ": " + e.getMessage());
    }
}
private void saveEventsToCSV(String fileName) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", java.util.Locale.ENGLISH);

    try (PrintWriter pw = new PrintWriter(fileName)) {
        pw.println("ID,Type,Name,Date,Time,VIP Price,Gold Price,Silver Price");

        Collection<Event> events = eventManager.getAllEvents();

        for (Event event : events) {
            pw.println(
                event.getId() + ","
                + event.getType() + ","
                + event.getName() + ","
                + event.getDate().format(dateFormatter) + ","
                + event.getTime().format(timeFormatter) + ","
                + event.getVipPrice() + ","
                + event.getGoldPrice() + ","
                + event.getSilverPrice()
            );
        }

        logger.log("Event CSV saved successfully to " + fileName);
    } catch (IOException e) {
        System.out.println("Error saving events CSV: " + e.getMessage());
        logger.log("Error saving events CSV to " + fileName + ": " + e.getMessage());
    }
}
private void saveVenuesToCSV(String fileName) {
    try (PrintWriter pw = new PrintWriter(fileName)) {
        pw.println("ID,Name,Type,Capacity,Concert Capacity,Cost,VIP Percent");

        Collection<Venue> venues = venueManager.getAllVenues();

        for (Venue venue : venues) {
            pw.println(
                venue.getId() + ","
                + venue.getName() + ","
                + venue.getType() + ","
                + venue.getCapacity() + ","
                + venue.getConcertCapacity() + ","
                + venue.getCost() + ","
                + venue.getVipPercent()
            );
        }

        logger.log("Venue CSV saved successfully to " + fileName);
    } catch (IOException e) {
        System.out.println("Error saving venues CSV: " + e.getMessage());
        logger.log("Error saving venues CSV to " + fileName + ": " + e.getMessage());
    }
}

}