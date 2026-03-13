/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents a Customer user in the TicketMiner system.
 */
public class Customer extends User {

    private double moneyAvailable;
    private boolean hasMembership;

    /**
     * Creates a new Customer.
     *
     * @param id customer ID
     * @param firstName customer first name
     * @param lastName customer last name
     * @param username login username
     * @param password login password
     * @param moneyAvailable available balance
     * @param hasMembership TicketMiner membership status
     */
    public Customer(int id, String firstName, String lastName,
                    String username, String password,
                    double moneyAvailable, boolean hasMembership) {

        super(id, firstName, lastName, username, password);

        this.moneyAvailable = moneyAvailable;
        this.hasMembership = hasMembership;
    }

    /**
     * Returns the customer's available balance.
     *
     * @return available money
     */
    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    /**
     * Returns whether the customer has a TicketMiner membership.
     *
     * @return membership status
     */
    public boolean getMembership() {
        return hasMembership;
    }

}