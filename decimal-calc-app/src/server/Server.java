package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A server for allowing clients to connect to the calculator application.
 */
public final class Server {
    
    // the port number of the socket
    private int port;
    
    // the socket that receives incoming client connections
    private ServerSocket server;
    
    // list of active client connection handlers
    private List<ConnectionHandler> connections;
    
    //
    protected final Calculator calculator;
    protected final DBHandler dbHandler;
    protected final EmailHandler emailHandler;
    
    /**
     * Construct a server with a specified port number.
     * 
     * @param port the port number.
     */
    public Server(int port) {
        try {
            this.port = port;
            this.server = new ServerSocket(8000);
            this.connections = Collections.synchronizedList(new LinkedList<>());
        }
        catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        
        this.calculator = Calculator.INSTANCE;
        this.dbHandler = new DBHandler("jdbc:mysql://localhost:3306/?user=root", "root", "");
        this.emailHandler = new EmailHandler();
    }
    
    /**
     * Create a client connection handler.
     * 
     * @param socket the client socket.
     * @return the connection handler.
     */
    private ConnectionHandler createConnection(Socket socket) {
        ConnectionHandler connection = new ConnectionHandler(this, socket);
        connection.start();
        System.out.println("SERVER::CONNECT \t" + connection);
        return connection;
    }
    
    /**
     * Remove a client connection handler.
     * 
     * @param connection the connection handler to remove.
     * @return true if the connection handler was removed, and false otherwise.
     */
    protected boolean remove(ConnectionHandler connection) {
        System.out.println("SERVER::DISCONNECT \t" + connection);
        return this.connections.remove(connection);
    }
    
    /**
     * Start the server and listen for incoming connections from clients.
     */
    public void start() {
        // Listen for incoming client connections
        while (true) {
            try {
                // Found a client connection
                Socket socket = this.server.accept();
                // Keep track of client connection
                this.connections.add(createConnection(socket));
            } 
            catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server(8000);
        server.start();
    }
}
