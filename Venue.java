/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Abstract class representing a venue where events take place.
 * A venue has a capacity, cost, and VIP seating percentage.
 */

public abstract class Venue {

    protected int id;
    protected String name;
    protected int capacity;
    protected int concertCapacity;
    protected double cost;
    protected double vipPercent;

    /**
     * Creates a new venue.
     */
    public Venue(int id, String name, int capacity, int concertCapacity, double cost, double vipPercent) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.concertCapacity = concertCapacity;
        this.cost = cost;
        this.vipPercent = vipPercent;
    }

    /** Updates the venue name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Updates the cost of the venue. */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /** Updates the venue capacity. */
    public void setCapacity(int capacity, int concertCapacity) {
        this.capacity = capacity;
        this.concertCapacity = concertCapacity;
    }

    /** Returns the venue ID. */
    public int getId() {
        return id;
    }

    /** Returns the venue name. */
    public String getName() {
        return name;
    }

    /** Returns the venue capacity. */
    public int getCapacity() {
        return capacity;
    }

    /** Returns the concert capacity. */
    public int getConcertCapacity() {
        return concertCapacity;
    }

    /** Returns the cost of the venue. */
    public double getCost() {
        return cost;
    }

    /** Returns the VIP seating percentage. */
    public double getVipPercent() {
        return vipPercent;
    }

    /** Returns the venue type (Arena, Stadium, etc). */
    public String getType() {
        return this.getClass().getSimpleName();
    }
}