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

    /** it is to add a venue if the id is not already present. */
    public boolean addVenue(Venue venue){
        if (venue ==null){
            return false;
        }
        int id= venue.getId();
        if(venuesById.containsKey(id)){
            return false; // venue with this id already exists
        }
        venuesById.put(id, venue);
        return true;
    }

    public Venue findById(int id){
        return venuesById.get(id);
    }

    public int size(){
        return venuesById.size();
    }

}