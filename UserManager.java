import java.util.HashMap;
import java.util.Collection;


/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */

public class UserManager {

    private HashMap<Integer, User> usersById;
    private HashMap<String, User> usersByUsername;

    public UserManager() {
        usersById = new HashMap<>();
        usersByUsername = new HashMap<>();
    }

        /** this adds a user if the username is unique and returns true if added, false otherwise. */
   public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        String uname = user.getUsername();
        if (uname == null) {
            return false;
        }

        if (usersByUsername.containsKey(uname)) {
            return false; // username already exists
        }

        usersById.put(user.getId(), user);
        usersByUsername.put(uname, user);
        return true;
    }

    public User findById(int id) {
        return usersById.get(id);
    }

    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return usersByUsername.get(username);
    }

    public boolean isUsernameUnique(String username) {
        if (username == null)  {
            return false;
        }
        return !usersByUsername.containsKey(username);
    }

    public int size() {
        return usersById.size();
    }
    /**
    * Regresa una colección con todos los usuarios almacenados.
    * 
    * Este método permite recorrer todos los usuarios del sistema
    * para mostrarlos o procesarlos.
    * 
    * @return colección de usuarios
    */
    public Collection<User> getAllUsers() {
        return usersById.values();
    }

    /**
      * Elimina un usuario a partir de su ID.
      * 
      * Si el usuario existe, también se elimina de la estructura
      * que organiza por username.
      * 
      * @param id identificador del usuario
      * @return true si el usuario fue eliminado, false en caso contrario
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
     * Busca un usuario por nombre completo.
     * 
     * La búsqueda compara first name y last name ignorando mayúsculas y minúsculas.
     * 
     * @param firstName nombre del usuario
     * @param lastName apellido del usuario
     * @return usuario encontrado o null si no existe
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
      * Elimina un usuario del sistema usando el objeto User.
      * 
      * @param user usuario a eliminar
      * @return true si se eliminó correctamente, false en caso contrario
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
     * Actualiza el username de un usuario y sincroniza la estructura
     * de búsqueda por username.
     * 
     * El cambio solo se realiza si el usuario existe y el nuevo username
     * no está siendo utilizado por otro miembro.
     * 
     * @param user usuario al que se le cambiará el username
     * @param newUsername nuevo username
     * @return true si el cambio se realizó, false en caso contrario
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

