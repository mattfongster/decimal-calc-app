package client;

import com.Command;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * A client that connects to the calculator application server.
 * 
 * @author
 */
public class Client {
   
    // The server host
    private final String host;
    
    // The server port number
    private final int port;
    
    // A client socket for server connection
    private Socket socket;
    
    // I/O streams for server communication
    private BufferedReader dataIn;
    private DataOutputStream dataOut;
    
    /**
     * Construct a client with the server's host and port number.
     * 
     * @param host the server host name.
     * @param port the port number.
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Start the client connection.
     * 
     * @return true if the client was able to connect to the server, and false
     * otherwise.
     */
    public boolean connect() {
        try {
            this.socket = new Socket(host, port);
            this.dataIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dataOut = new DataOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Close the client connection.
     */
    public void disconnect() {
        try {
            // Send disconnect message
            this.dataOut.writeBytes(Command.disconnectCommand() + "\n");
            
            // Close client socket
            this.socket.close();
        } 
        catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    /**
     * Check if the client is connected to the server.
     * 
     * @return true if the client is connected to the server, and false otherwise.
     */
    public boolean isConnected() {
        return this.socket.isConnected();
    }
    
    /**
     * Send a message to the server.
     * 
     * @param message the message to send.
     * @return a response from the server.
     */
    public String sendMessage(String message) {
        String response = "";
        try {
            // Send message to server
            this.dataOut.writeBytes(message + "\n");
            this.dataOut.flush();
           
            // Receive response from server
            do {
                response = this.dataIn.readLine();
            } while (response.equals(""));
        }
        catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        return response;
    }
}
