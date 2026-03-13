/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents a Stadium venue in the TicketMiner system.
 */

/**
 * Creates a new Stadium venue.
 *
 * @param id venue ID
 * @param name venue name
 * @param capacity total capacity
 * @param concertCapacity capacity for concerts
 * @param cost venue cost
 * @param vipPercent percentage of VIP seating
 */
public class Stadium extends Venue {
    public Stadium(int id, String name, int capacity, int concertCapacity, double cost, double vipPercent){
        super(id, name, capacity, concertCapacity, cost, vipPercent);
    }
}