import java.util.Collection;
import java.util.HashMap;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public class VenueManager {
    private HashMap<Integer, Venue> venuesById;

    public VenueManager() {
        venuesById = new HashMap<>();
    }

    /** Add a venue if the id is not already present. */
    public boolean addVenue(Venue venue) {
        if (venue == null) {
            return false;
        }

        int id = venue.getId();

        if (venuesById.containsKey(id)) {
            return false;
        }

        venuesById.put(id, venue);
        return true;
    }

    public Venue findById(int id) {
        return venuesById.get(id);
    }

    public Venue findByName(String name) {
        for (Venue venue : venuesById.values()) {
            if (venue.getName().equalsIgnoreCase(name)) {
                return venue;
            }
        }
        return null;
    }

    public Venue findByType(String type) {
        for (Venue venue : venuesById.values()) {
            if (venue.getType().equalsIgnoreCase(type)) {
                return venue;
            }
        }
        return null;
    }

    public boolean deleteVenue(Venue venue) {
        if (venue == null) {
            return false;
        }
        return venuesById.remove(venue.getId()) != null;
    }

    public Collection<Venue> getAllVenues() {
        return venuesById.values();
    }

    public int size() {
        return venuesById.size();
    }

    public int getNextId() {
    int max = 0;

    for (Venue venue : venuesById.values()) {
        if (venue.getId() > max) {
            max = venue.getId();
        }
    }

    return max + 1;
}
}