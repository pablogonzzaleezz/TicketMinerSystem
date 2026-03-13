/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents a concert event in the TicketMiner system.
 */

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Event {

    protected int id;
    protected String name;
    protected LocalDate date;
    protected LocalTime time;
    protected double vipPrice;
    protected double goldPrice;
    protected double silverPrice;

    /**
     * Creates a new event.
     */
    public Event(int id, String name, LocalDate date, LocalTime time,
                 double vipPrice, double goldPrice, double silverPrice) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
    }

    /** Updates the event name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Updates the event date and time. */
    public void setDate(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    /** Returns the event ID. */
    public int getId() {
        return id;
    }

    /** Returns the event name. */
    public String getName() {
        return name;
    }

    /** Returns the event date. */
    public LocalDate getDate() {
        return date;
    }

    /** Returns the event time. */
    public LocalTime getTime() {
        return time;
    }

    /** Returns the VIP ticket price. */
    public double getVipPrice() {
        return vipPrice;
    }

    /** Returns the Gold ticket price. */
    public double getGoldPrice() {
        return goldPrice;
    }

    /** Returns the Silver ticket price. */
    public double getSilverPrice() {
        return silverPrice;
    }

    /** Returns the type of event (Concert, Sport, Special). */
    public String getType() {
        return this.getClass().getSimpleName();
    }
}