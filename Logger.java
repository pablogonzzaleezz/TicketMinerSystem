import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * CS 3331 – Advanced Object-Oriented Programming
 * Project Part 1 – TicketMiner
 *
 * Handles logging messages to a log file with timestamps.
 */
public class Logger {

    private String logFileName;

    /**
     * Creates a new Logger that writes messages to a specified file.
     *
     * @param logFileName name of the log file
     */
    public Logger(String logFileName){
        this.logFileName = logFileName;
    }

    /**
     * Writes a message to the log file with the current timestamp.
     *
     * @param message the message to log
     */
    public void log(String message){
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            String timestamp = LocalDateTime.now().toString();
            writer.write("[" + timestamp + "] " + message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

}