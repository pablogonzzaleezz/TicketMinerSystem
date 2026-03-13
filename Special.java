/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Represents a special event in the TicketMiner system.
 */
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Creates a new Special event.
 *
 * @param id event ID
 * @param name event name
 * @param date event date
 * @param time event time
 * @param vipPrice VIP ticket price
 * @param goldPrice Gold ticket price
 * @param silverPrice Silver ticket price
 */
public class Special extends Event{

    public Special(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice){
        super(id, name, date, time, vipPrice, goldPrice, silverPrice);
    }
    
}
