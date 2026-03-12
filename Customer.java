/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */

public class Customer extends User {

    private double moneyAvailable;
    private boolean hasMembership;

    public Customer(int id, String firstName, String lastName,
                    String username, String password,
                    double moneyAvailable, boolean hasMembership) {

        super(id, firstName, lastName, username, password);

        this.moneyAvailable = moneyAvailable;
        this.hasMembership = hasMembership;
    }
    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    public boolean getMembership() {
        return hasMembership;
    }

}