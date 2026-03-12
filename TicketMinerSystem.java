import java.util.Collection;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
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
     * Constructor principal del sistema TicketMiner.
     * 
     * Inicializa los administradores de usuarios, eventos y venues,
     * configura el logger, crea el scanner para leer entradas del usuario,
     * carga la información inicial desde los archivos CSV
     * y crea un administrador por defecto si no existe.
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
     * Carga toda la información inicial del sistema desde los archivos CSV.
     * 
     * Este método carga:
     * - clientes
     * - eventos
     * - venues
     * 
     * Además, registra en el log cuántos elementos fueron cargados.
     */
    private void loadAllData() {
        loadCustomersFromCSV("Customer_List_PA1.csv");
        loadEventsFromCSV("Event_List_PA1.csv");
        loadVenuesFromCSV("Venue_List_PA1.csv");

        logger.log("Initial data loaded. Users=" + userManager.size()
                + ", Events=" + eventManager.size()
                + ", Venues=" + venueManager.size());
    }

    /**
     * Carga los clientes desde un archivo CSV y los agrega al UserManager.
     * 
     * El archivo debe contener la información necesaria para construir
     * objetos de tipo Customer.
     * 
     * @param fileName nombre del archivo CSV de clientes
     */
    private void loadCustomersFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Se omite la línea del encabezado

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
                double moneyAvailable = Double.parseDouble(parts[5].trim());
                boolean hasMembership = Boolean.parseBoolean(parts[6].trim());

                Customer customer = new Customer(
                        id,
                        firstName,
                        lastName,
                        username,
                        password,
                        moneyAvailable,
                        hasMembership
                );

                userManager.addUser(customer);
            }

            logger.log("Customers loaded successfully from " + fileName);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading customers from CSV: " + e.getMessage());
            logger.log("Error loading customers from " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Carga los eventos desde un archivo CSV y los agrega al EventManager.
     * 
     * Dependiendo del tipo de evento leído en el archivo,
     * se crea un objeto Concert, Sport o Special.
     * 
     * @param fileName nombre del archivo CSV de eventos
     */
    private void loadEventsFromCSV(String fileName) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Se omite la línea del encabezado

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
     * Carga los venues desde un archivo CSV y los agrega al VenueManager.
     * 
     * Dependiendo del tipo de venue leído en el archivo,
     * se crea un objeto Arena, Auditorium, Stadium o OpenAir.
     * 
     * @param fileName nombre del archivo CSV de venues
     */
    private void loadVenuesFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Se omite la línea del encabezado

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                String name = parts[2].trim();
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
     * Crea un administrador por defecto si todavía no existe uno con username "admin".
     * 
     * Esto permite acceder al sistema como administrador mientras se implementan
     * las demás funcionalidades del proyecto.
     */
    private void seedDefaultAdmin() {
        if (userManager.findByUsername("admin") == null) {
            Admin admin = new Admin(9999, "System", "Admin", "admin", "admin123");
            userManager.addUser(admin);
            logger.log("Default admin account created: username=admin");
        }
    }

    public void start(){
        logger.log("TicketMiner System started.");
        
        // Muestra en pantalla un resumen de la información cargada al iniciar el sistema
        System.out.println("\nData loaded successfully.");
        System.out.println("Users loaded: " + userManager.size());
        System.out.println("Events loaded: " + eventManager.size());
        System.out.println("Venues loaded: " + venueManager.size());

        boolean runing = true;
        while (runing){
            printMainMenu();
            String choice = myScanner.nextLine().trim();

            if(choice.equals("1")){
                handleRegister();
            }
            else if(choice.equals("2")){
                handleLogin();
            }
            else if(choice.equalsIgnoreCase("EXIT")){
                handleExit();
                runing = false;
            }
            else{
                System.out.println("Upps! Invalid choice. Please try again.");
            }

    }
    logger.log("TicketMiner System terminated.");
    }


    private void printMainMenu() {
        System.out.println("\n========== TicketMiner Main Menu ==========");
        System.out.println("\n1) Register");
        System.out.println("2) Login");
        System.out.println("Type EXIT to save and quit");
        System.out.println("\n==========================================");
        System.out.print("\nSelect an option: ");
    }

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
            logger.log("Upps! Failed Customer registration attempt for username " + username);
        }
    }

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
            System.out.println("Upps! Registration failed (username already exists).");
            logger.log("Upps! Failed Organizer registration attempt for username " + username);
        }
    }



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
            logger.log("Upps! Login failed: incorrect password for username " + username);
            return;
        }

        System.out.println("\nLogin successful. Welcome, " + user.getUsername() + "!");
        logger.log("Login successful for username " + username);

        // Placeholder: role menus later
        if (user instanceof Admin) {
            showAdminMenu();
        } else if (user instanceof Organizer) {
            System.out.println("[Organizer menu placeholder]");
        } else if (user instanceof Customer) {
            System.out.println("[Customer menu placeholder]");
        }
    }

    private void handleExit() {
        System.out.println("\nSaving updated CSV files (placeholder)...");
        logger.log("EXIT selected. Saving updated CSV files (placeholder).");
    }

    // --- Helper methods ---

    private int generateUserId() {
        // Simple placeholder: ID = current size + 1
        return userManager.size() + 1;
    }

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

    private boolean readBooleanSafe() {
        while (true) {
            String input = myScanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("t") || input.equals("yes") || input.equals("y")) return true;
            if (input.equals("false") || input.equals("f") || input.equals("no") || input.equals("n")) return false;

            System.out.print("\nUpps! Invalid input. Enter true/false: ");
        }
    }
    /**
      * Muestra el menú del administrador y permite seleccionar
      * las diferentes opciones de administración del sistema.
      * 
      * Este menú permite gestionar:
      * - Usuarios
      * - Venues
      * - Eventos
      * 
      * El menú se repite hasta que el administrador decida cerrar sesión.
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

             System.out.println("\n[Manage Venues placeholder]");

            } else if (choice.equals("3")) {

             System.out.println("\n[Manage Events placeholder]");

            } else if (choice.equals("4")) {

             System.out.println("\nLogging out...");
             running = false;

            } else {

             System.out.println("\nInvalid option. Try again.");

            }
        }
    }
    /**
      * Muestra el submenú de administración de usuarios.
      * 
      * Este menú permite al administrador:
      * - agregar usuarios
      * - visualizar usuarios
      * - actualizar usuarios
      * - eliminar usuarios
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
            }
            else if (choice.equals("2")) {
                showViewUsersMenu();
            }
            else if (choice.equals("3")) {
                adminUpdateUser();
            }
            else if (choice.equals("4")) {
                adminDeleteUser();
            }
            else if (choice.equals("5")) {
                running = false;
            }
            else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }
 

    /**
      * Permite al administrador agregar un nuevo usuario.
      * 
      * El administrador puede crear usuarios de tipo Customer,
      * Organizer o Admin.
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
        }
        else if (choice.equals("2")) {
            Organizer organizer = new Organizer(id, firstName, lastName, username, password);

            if (userManager.addUser(organizer)) {
                System.out.println("\nOrganizer added successfully.");
                logger.log("Admin added Organizer with username " + username);
            } else {
                System.out.println("\nFailed to add organizer.");
            }
        }
        else if (choice.equals("3")) {
            Admin admin = new Admin(id, firstName, lastName, username, password);

            if (userManager.addUser(admin)) {
                System.out.println("\nAdmin added successfully.");
                logger.log("Admin added Admin with username " + username);
            } else {
                System.out.println("\nFailed to add admin.");
            }
        }
        else {
            System.out.println("\nInvalid option.");
        }
    }
    /**
      * Muestra el submenú de visualización de usuarios.
      * 
      * Este menú permite:
      * - mostrar todos los miembros
      * - buscar un miembro específico
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
            }
            else if (choice.equals("2")) {
                adminSearchUser();
            }
            else if (choice.equals("3")) {
                running = false;
            }
            else {
                System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    /**
      * Permite al administrador buscar un usuario por ID,
      * nombre o username.
      * 
      * Si el usuario es encontrado, el sistema muestra su
      * información correspondiente. Si no se encuentra,
      * se muestra un mensaje informativo.
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

        }
        else if (choice.equals("2")) {

            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);

        }
        else if (choice.equals("3")) {

            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();

            user = userManager.findByUsername(username);

        }
        else {

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
        }
        else if (user instanceof Organizer) {
            System.out.println("Category: Organizer");
        }
        else if (user instanceof Admin) {
            System.out.println("Category: Admin");
        }
    }
    /**
      * Muestra todos los miembros registrados en el sistema.
      * 
      * El método recorre todos los usuarios almacenados en el UserManager
      * y despliega toda la información correspondiente a cada miembro.
      * 
      * Si no existen usuarios registrados, se muestra un mensaje indicando
      * que no hay miembros en el sistema.
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
      * Lee un número entero de forma segura.
      * 
      * Si el usuario escribe un valor inválido,
      * se vuelve a solicitar la entrada.
      * 
      * @return número entero válido
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
      * Permite al administrador eliminar un usuario buscando por ID,
      * nombre o username.
      * 
      * Primero se localiza al usuario. Si existe, el sistema pide
      * confirmación antes de eliminarlo.
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
        }
        else if (choice.equals("2")) {
            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);
        }
        else if (choice.equals("3")) {
            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();

            user = userManager.findByUsername(username);
        }
        else {
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
            }
            else {
                System.out.println("\nFailed to delete member.");
            }
        }
        else {
            System.out.println("\nDeletion cancelled.");
        }
    }
    /**
     * Permite al administrador actualizar la información de un miembro.
     * 
     * El administrador puede buscar al miembro por:
     * - ID
     * - Name
     * - Username
     * 
     * Si el miembro es encontrado, el sistema permite:
     * - cambiar nombre
     * - cambiar username
     * - cambiar password
     * 
     * Si el miembro no es encontrado, se muestra un mensaje informativo.
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

        }
        else if (choice.equals("2")) {

            System.out.print("\nEnter first name: ");
            String firstName = myScanner.nextLine().trim();

            System.out.print("\nEnter last name: ");
            String lastName = myScanner.nextLine().trim();

            user = userManager.findByName(firstName, lastName);

        }
        else if (choice.equals("3")) {

            System.out.print("\nEnter username: ");
            String username = myScanner.nextLine().trim();

            user = userManager.findByUsername(username);

        }
        else {
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

        }
        else if (updateChoice.equals("2")) {

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
                }
                else {
                    System.out.println("\nUsername already exists. Enter a different username.");
                }
            }

        }
        else if (updateChoice.equals("3")) {

            System.out.print("\nEnter new password: ");
            String newPassword = myScanner.nextLine().trim();

            user.setPassword(newPassword);

            System.out.println("\nPassword updated successfully.");
            logger.log("Admin updated password for user ID " + user.getId());

        }
        else {
            System.out.println("\nInvalid option.");
        }
    }




}
