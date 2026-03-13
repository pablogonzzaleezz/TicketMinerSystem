/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public abstract class Venue {

    protected int id;
    protected String name;
    protected int capacity;
    protected int concertCapacity;
    protected double cost;
    protected double vipPercent;

    public Venue(int id, String name, int capacity, int concertCapacity, double cost, double vipPercent) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.concertCapacity = concertCapacity;
        this.cost = cost;
        this.vipPercent = vipPercent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCapacity(int capacity, int concertCapacity) {
        this.capacity = capacity;
        this.concertCapacity = concertCapacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getConcertCapacity() {
        return concertCapacity;
    }

    public double getCost() {
        return cost;
    }

    public double getVipPercent() {
        return vipPercent;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}