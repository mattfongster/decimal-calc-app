package server;

import com.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHandler {
    
    private final static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private final static Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
    private static final String SELECT          = "select * from decimal_calc.users";
    private static final String INSERT          = "insert into decimal_calc.users values ('%s', '%s', '%s', %d)";
    private static final String UPDATE_ATTEMPTS = "update decimal_calc.users set login_attempts = %d where username = '%s'";
    private static final String UPDATE_PASSWORD = "update decimal_calc.users set password = '%s' where username = '%s'";
    
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private Connection connection;
    
    public DBHandler(String dbUrl, String dbUser, String dbPassword) {
        try {
            this.dbUrl = dbUrl;
            this.dbUser = dbUser;
            this.dbPassword = dbPassword;
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    protected static boolean validPassword(String password)
    {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.find();
    }

    protected static boolean validEmailAddress(String emailaddress)
    {
        Matcher matcher = EMAIL_PATTERN.matcher(emailaddress);
        return matcher.find();
    }
    
    public String register(String username, String password, String email) {
        try {
            // Check if the username is already in the database
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                String u = rs.getString("username");
                if (u.equals(username)) {
                    return Result.REGISTER_DUPLICATE_USER;
                }
            }
            
            // Insert new account information into the database
            String query = String.format(INSERT, username, password, email, 0);            
            stmt.executeUpdate(query);
        }
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        return Result.REGISTER_SUCCESS;
    }
    
    public String getEmail(String username) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("SELECT email_address FROM decimal_calc.users WHERE username = '" + username + "'");
            ResultSet rs = stmt.executeQuery();
            return (rs.first()) ? rs.getString("email_address") : null;
        } 
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }
    
    public String validateAccount(String username, String password) {
        try {
//            String query = "SELECT password FROM decimal_calc.users WHERE username = %s";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM decimal_calc.users");
            
            while (rs.next()) {
                String u = rs.getString("username");
                String p = rs.getString("password");
                if (u.equals(username)) {
                    if (rs.getInt("login_attempts") >= 3) {
                        return Result.LOGIN_TOO_MANY_ATTEMPTS;
                    }
                    if (p.equals(password)) {
                        this.resetLoginAttempts(stmt, username, 0);
                        return Result.LOGIN_SUCCESS; 
                    }
                    else {
                        this.resetLoginAttempts(stmt, username, rs.getInt("login_attempts") + 1);
                        return Result.LOGIN_BAD_PASSWORD;
                    }
                }
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
        return Result.LOGIN_BAD_USERNAME;
    }
    
    public void resetLoginAttempts(Statement stmt, String username, int loginAttempt) {
        try {
            String updateQuery = String.format(UPDATE_ATTEMPTS, loginAttempt, username);
            stmt.executeUpdate(updateQuery);
        } 
        catch (SQLException ex) {
            System.exit(1);
        }
    }
    
    public void resetPassword(String username, String password) {
        try {
            String updateQuery = String.format(UPDATE_PASSWORD, password, username);
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(updateQuery);
        } 
        catch (SQLException ex) {
            System.exit(1);
        }
    }
    
    protected boolean isDuplicateUsername(String username) throws SQLException
    {
        ResultSet rs = getResultSetForUsername(username);
        return rs.first();
    }

    protected boolean isPasswordCorrect(String username, String password) throws SQLException
    {
        ResultSet rs = getResultSetForUsername(username);
        
        while(rs.next())
        {
            String column = rs.getString("password");
            if(column.equals(password))
            {
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean isAbleToLogin(String username) throws SQLException
    {
        ResultSet rs = getResultSetForUsername(username);
        
        while(rs.next())
        {
            String column = rs.getString("login_attempts");
            int logAttempts = Integer.parseInt(column);
            
            if(logAttempts < 4)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private ResultSet getResultSetForUsername(String username) throws SQLException
    {
        String query = String.format("SELECT * FROM decimal_calc.users WHERE username = '%s'", username);
        PreparedStatement stmt = this.connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
