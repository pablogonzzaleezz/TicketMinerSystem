/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Abstract class representing a system user.
 * A user has an ID, name, username, and password.
 */

public abstract class User {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    /**
     * Creates a new user.
     *
     * @param id user ID
     * @param firstName user's first name
     * @param lastName user's last name
     * @param username login username
     * @param password login password
     */
    public User(int id, String firstName, String lastName,
                String username, String password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /**
     * Checks if the password matches the stored password.
     *
     * @param pw password entered by user
     * @return true if password matches
     */
    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }

    /** Updates the username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Updates the password. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Returns the user ID. */
    public int getId() {
        return id;
    }

    /** Returns the username. */
    public String getUsername(){
        return username;
    }

    /** Returns the user's first name. */
    public String getFirstName() {
        return firstName;
    }

    /** Returns the user's last name. */
    public String getLastName() {
        return lastName;
    }

    /** Updates the user's first name. */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Updates the user's last name. */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Returns the user's password. */
    public String getPassword() {
        return password;
    }
}