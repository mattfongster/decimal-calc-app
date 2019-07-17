package server;

import com.Command;
import com.Result;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;

/**
 * A handler that manages a client's connection and communication with a server.
 */
public final class ConnectionHandler extends Thread {
    
    private Socket socket;
    private Server server;
    private boolean keepAlive;
    private BufferedReader dataIn;
    private DataOutputStream dataOut;
    
    /**
     * Construct a connection handler.
     * 
     * @param server the server that the handler is a part of.
     * @param socket the client socket that the handler manages.
     */
    public ConnectionHandler(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.keepAlive = true;
            this.dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.dataOut = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    /**
     * Close the connection handler.
     */
    private void close() {
        try {
            this.dataIn.close();
            this.dataOut.close();
            this.keepAlive = false;
        } 
        catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
        
    private String respond(String[] message) {
        String output = "";
        switch (message[1]) {
            case Command.LOGIN:
                String out;
                String username = message[2];
                String password = message[3];
                DBHandler dbh = this.server.dbHandler;
                try {
                    if (dbh.isDuplicateUsername(username)) // does username exist?
                    {
                        if (dbh.isAbleToLogin(username)) // does it have the right amount of attempts?
                        {
                            if (dbh.isPasswordCorrect(username, password)) // is the password valid?
                            {
                                out = Result.LOGIN_SUCCESS;
                            } else {
                                out = Result.LOGIN_BAD_PASSWORD;
                            }
                        } else {
                            out = Result.LOGIN_TOO_MANY_ATTEMPTS;
                        }
                    } else {
                        out = Result.LOGIN_BAD_USERNAME;
                    }

                    output = out;
                } catch (SQLException e) {
                    e.printStackTrace(System.err);
                }
                break;
            case Command.CALCULATE:
                try {
                    output = this.server.calculator.evaluate(message[2]) + "\n";
                } 
                catch (Exception ex) {
                    output = Result.CALC_BAD_EXPR;
                }
                break;
            case Command.GAME:
                try {
                    output = this.server.calculator.evaluate(message[2]) + "\n";
                } 
                catch (Exception ex) {
                    output = Result.GAME_BAD_EXPR;
                }
                break;
            case Command.REGISTER:
                output = this.server.dbHandler.register(message[2], message[3], message[4]) + "\n";
                break;
            case Command.PASSWORD_RECOVERY:
                String email = this.server.dbHandler.getEmail(message[2]);
                if (email != null) {
                    String newPassword = this.server.emailHandler.sendRecoveryEmail(email);
                    this.server.dbHandler.resetPassword(message[2], newPassword);
                    output = Result.PASSWORD_REC_SUCCESS;
                } else {
                    output = Result.PASSWORD_REC_BAD_USERNAME;
                }
                break;
            default:
                output = Result.UNKNOWN_COMMAND + "\n";
                break;
        }
        return output;
    }
    
    @Override
    public void run() {
        while (this.keepAlive) {
            try {
                // Get messages from the client          
                String[] message = dataIn.readLine().split(Command.DELIMITER);
                
                if (message[1].equals(Command.DISCONNECT)) {
                    this.server.remove(this);   // Remove connection handler from server
                    this.keepAlive = false;     // Stop connection handler.
                    continue;
                }
                
                // Respond to client
                String output = this.respond(message);
                this.dataOut.writeBytes(output + "\n");
                this.dataOut.flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    @Override
    public String toString() {
        return this.socket.toString();
    }
}
