/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents an organizer user in the TicketMiner system.
 */


/**
 * Creates a new Organizer.
 *
 * @param id organizer ID
 * @param firstName organizer first name
 * @param lastName organizer last name
 * @param username login username
 * @param password login password
 */
public class Organizer extends User {

    public Organizer(int id, String firstName, String lastName,
                     String username, String password) {

        super(id, firstName, lastName, username, password);
    }

}