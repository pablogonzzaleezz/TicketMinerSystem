/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents an administrator user in the TicketMiner system.
 */
public class Admin extends User {

    /**
     * Creates a new Admin user.
     *
     * @param id admin ID
     * @param firstName admin first name
     * @param lastName admin last name
     * @param username login username
     * @param password login password
     */
    public Admin(int id, String firstName, String lastName, String username, String password){
        super(id, firstName, lastName, username, password);
    }

}