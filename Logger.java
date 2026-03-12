import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Name: Pablo Gonzalez, Sebastian Flores
 * Course: CS 3331 – Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: TicketMiner – Project Part 1
 */
public class Logger{

    private String logFileName;

    public Logger(String logFileName){
        this.logFileName = logFileName;
    }

    public void log(String message){
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            String timestamp = LocalDateTime.now().toString();
            writer.write("[" + timestamp + "] " + message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    
}
