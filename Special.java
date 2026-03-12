/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class Special extends Event{

    public Special(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice){
        super(id, name, date, time, vipPrice, goldPrice, silverPrice);
    }
    
}
