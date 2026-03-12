/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */

public abstract class User {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    public User(int id, String firstName, String lastName,
                String username, String password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getUsername(){
        return username;
    }
    /**
      * Regresa el nombre del usuario.
      * 
      * @return first name del usuario
      */
    public String getFirstName() {
        return firstName;
    }

    /**
      * Regresa el apellido del usuario.
      * 
      * @return last name del usuario
      */
    public String getLastName() {
        return lastName;
    }
    /**
     * Actualiza el nombre del usuario.
     * 
     * @param firstName nuevo nombre
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Actualiza el apellido del usuario.
     * 
     * @param lastName nuevo apellido
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}