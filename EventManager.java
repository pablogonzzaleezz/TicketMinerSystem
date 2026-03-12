import java.util.HashMap;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public class EventManager{

    private HashMap<Integer, Event> eventsById;

    public EventManager(){
        eventsById = new HashMap<>();
    }

        /** this is adding an event if the id is not already present. */
    public boolean addEvent(Event event){
        if (event ==null){
            return false;

        }
        int id = event.getId();
        if (eventsById.containsKey(id)){
            return false; // event with this id already exists
        }
        eventsById.put(id, event);
        return true;
    }

    public Event findById(int id){
        return eventsById.get(id);
    }

    public int size(){
        return eventsById.size();
    }

}