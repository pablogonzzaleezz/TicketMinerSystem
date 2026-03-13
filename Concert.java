/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents a concert event in the TicketMiner system.
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class Concert extends Event {

    /**
     * Creates a new Concert event.
     *
     * @param id event ID
     * @param name event name
     * @param date event date
     * @param time event time
     * @param vipPrice VIP ticket price
     * @param goldPrice Gold ticket price
     * @param silverPrice Silver ticket price
     */
    public Concert(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice){
        super(id, name, date, time, vipPrice, goldPrice, silverPrice);
    }

}