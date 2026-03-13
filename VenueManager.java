import java.util.Collection;
import java.util.HashMap;

/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Manages all venues in the system.
 */
public class VenueManager {

    private HashMap<Integer, Venue> venuesById;

    /**
     * Creates an empty venue manager.
     */
    public VenueManager() {
        venuesById = new HashMap<>();
    }

    /**
     * Adds a venue if the ID is not already present.
     *
     * @param venue the venue to add
     * @return true if added successfully, false otherwise
     */
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

    /**
     * Finds a venue by ID.
     *
     * @param id venue ID
     * @return the venue if found, otherwise null
     */
    public Venue findById(int id) {
        return venuesById.get(id);
    }

    /**
     * Finds a venue by name.
     *
     * @param name venue name
     * @return the venue if found, otherwise null
     */
    public Venue findByName(String name) {
        for (Venue venue : venuesById.values()) {
            if (venue.getName().equalsIgnoreCase(name)) {
                return venue;
            }
        }
        return null;
    }

    /**
     * Finds a venue by type (Arena, Stadium, etc.).
     *
     * @param type venue type
     * @return the venue if found, otherwise null
     */
    public Venue findByType(String type) {
        for (Venue venue : venuesById.values()) {
            if (venue.getType().equalsIgnoreCase(type)) {
                return venue;
            }
        }
        return null;
    }

    /**
     * Deletes a venue from the system.
     *
     * @param venue venue to remove
     * @return true if removed successfully
     */
    public boolean deleteVenue(Venue venue) {
        if (venue == null) {
            return false;
        }
        return venuesById.remove(venue.getId()) != null;
    }

    /**
     * Returns all venues stored in the system.
     *
     * @return collection of venues
     */
    public Collection<Venue> getAllVenues() {
        return venuesById.values();
    }

    /**
     * Returns the number of venues stored.
     *
     * @return venue count
     */
    public int size() {
        return venuesById.size();
    }

    /**
     * Returns the next available venue ID.
     *
     * @return next venue ID
     */
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