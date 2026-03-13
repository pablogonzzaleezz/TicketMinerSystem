import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public class EventManager {

    private HashMap<Integer, Event> eventsById;

    public EventManager() {
        eventsById = new HashMap<>();
    }

    /**
     * Adds an event if it is not null and its ID does not already exist.
     *
     * @param event the event to add
     * @return true if added successfully, false otherwise
     */
    public boolean addEvent(Event event) {
        if (event == null) {
            return false;
        }

        int id = event.getId();

        if (eventsById.containsKey(id)) {
            return false;
        }

        eventsById.put(id, event);
        return true;
    }

    /**
     * Finds an event by ID.
     *
     * @param id the event ID
     * @return the event if found, otherwise null
     */
    public Event findById(int id) {
        return eventsById.get(id);
    }

    /**
     * Finds an event by name.
     *
     * @param name the event name
     * @return the first matching event if found, otherwise null
     */
    public Event findByName(String name) {
        for (Event event : eventsById.values()) {
            if (event.getName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Finds an event by date.
     *
     * @param date the event date
     * @return the first matching event if found, otherwise null
     */
    public Event findByDate(LocalDate date) {
        for (Event event : eventsById.values()) {
            if (event.getDate().equals(date)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Deletes an event from the system.
     *
     * @param event the event to delete
     * @return true if removed, false otherwise
     */
    public boolean deleteEvent(Event event) {
        if (event == null) {
            return false;
        }

        return eventsById.remove(event.getId()) != null;
    }

    /**
     * Returns all events currently stored in the system.
     *
     * @return a collection of all events
     */
    public Collection<Event> getAllEvents() {
        return eventsById.values();
    }

    /**
     * Returns the number of events stored in the system.
     *
     * @return event count
     */
    public int size() {
        return eventsById.size();
    }

    public int getNextId() {
    int max = 0;

    for (Event event : eventsById.values()) {
        if (event.getId() > max) {
            max = event.getId();
        }
    }

    return max + 1;
}

public List<Event> findAllByDate(LocalDate date) {
    List<Event> matches = new ArrayList<>();

    for (Event event : eventsById.values()) {
        if (event.getDate().equals(date)) {
            matches.add(event);
        }
    }

    return matches;
}
}