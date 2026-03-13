import java.util.HashMap;
import java.util.Collection;

/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Manages all users in the system using HashMaps.
 */
public class UserManager {

    private HashMap<Integer, User> usersById;
    private HashMap<String, User> usersByUsername;

    /**
     * Creates an empty user manager.
     */
    public UserManager() {
        usersById = new HashMap<>();
        usersByUsername = new HashMap<>();
    }

    /**
     * Adds a user if the username is unique.
     *
     * @param user the user to add
     * @return true if the user was added, false otherwise
     */
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        String uname = user.getUsername();
        if (uname == null) {
            return false;
        }

        if (usersByUsername.containsKey(uname)) {
            return false;
        }

        usersById.put(user.getId(), user);
        usersByUsername.put(uname, user);
        return true;
    }

    /**
     * Finds a user by ID.
     *
     * @param id user ID
     * @return the matching user, or null if not found
     */
    public User findById(int id) {
        return usersById.get(id);
    }

    /**
     * Finds a user by username.
     *
     * @param username username to search
     * @return the matching user, or null if not found
     */
    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return usersByUsername.get(username);
    }

    /**
     * Checks whether a username is unique.
     *
     * @param username username to check
     * @return true if the username is available
     */
    public boolean isUsernameUnique(String username) {
        if (username == null)  {
            return false;
        }
        return !usersByUsername.containsKey(username);
    }

    /**
     * Returns the number of users in the system.
     *
     * @return total number of users
     */
    public int size() {
        return usersById.size();
    }

    /**
     * Returns all users stored in the system.
     *
     * @return collection of users
     */
    public Collection<User> getAllUsers() {
        return usersById.values();
    }

    /**
     * Deletes a user by ID.
     *
     * @param id user ID
     * @return true if the user was removed, false otherwise
     */
    public boolean deleteUserById(int id) {
        User removedUser = usersById.remove(id);

        if (removedUser == null) {
            return false;
        }

        usersByUsername.remove(removedUser.getUsername());
        return true;
    }

    /**
     * Finds a user by first name and last name.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @return the matching user, or null if not found
     */
    public User findByName(String firstName, String lastName) {
        for (User user : usersById.values()) {
            if (user.getFirstName().equalsIgnoreCase(firstName)
                    && user.getLastName().equalsIgnoreCase(lastName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Deletes a user from the system.
     *
     * @param user user to delete
     * @return true if removed successfully, false otherwise
     */
    public boolean deleteUser(User user) {
        if (user == null) {
            return false;
        }

        usersById.remove(user.getId());
        usersByUsername.remove(user.getUsername());
        return true;
    }

    /**
     * Updates a user's username if the new username is unique.
     *
     * @param user user to update
     * @param newUsername new username
     * @return true if updated successfully, false otherwise
     */
    public boolean updateUsername(User user, String newUsername) {
        if (user == null || newUsername == null || newUsername.trim().isEmpty()) {
            return false;
        }

        newUsername = newUsername.trim();

        if (usersByUsername.containsKey(newUsername)) {
            return false;
        }

        usersByUsername.remove(user.getUsername());
        user.setUsername(newUsername);
        usersByUsername.put(newUsername, user);

        return true;
    }

    /**
     * Returns the next available user ID.
     *
     * @return next user ID
     */
    public int getNextId() {
        int max = 0;

        for (User user : usersById.values()) {
            if (user.getId() > max) {
                max = user.getId();
            }
        }

        return max + 1;
    }
}