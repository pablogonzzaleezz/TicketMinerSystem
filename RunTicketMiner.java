/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * This class contains the main method that starts the TicketMiner system.
 */
public class RunTicketMiner {

    /**
     * Starts the TicketMiner program.
     */
    public static void main(String[] args) {
        TicketMinerSystem system = new TicketMinerSystem();
        system.start();
        System.out.println("Thank you for using TicketMiner. Goodbye!");
    }
}