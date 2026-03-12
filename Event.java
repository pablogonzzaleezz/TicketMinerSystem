/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
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

    public Event(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice){
        this.id =id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDate(LocalDate date, LocalTime time){
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    


}