package isys1118.group1.server.database;

/**
 * <p>Implements Database-related functions.</p>
 * 
 * <p>Used to access and modify information related to a database.</p>
 * 
 * <p><b>NOTE: all functions in this class are placeholders ONLY!</b> These will
 * be used to properly connect to a database and get / set information at a
 * later date.</p>
 * 
 * <p><b>Creator:</b><br />
 * Name: Luke Larobina<br />
 * Student Number: s3287121<br />
 * Email: s3287121@student.rmit.edu.au</p>
 *
 */
public class Database
{
    
    public static int DATABASE_NULL = 0x1001;
    public static int DATABASE_NOT_CONNECTED = 0x1002;
    public static int DATABASE_CONNECTED = 0x1003;
    
    private static Database db;
    
    /**
     * Creates a database connection using a singleton method. This needs to be
     * called before any database functions can work.
     */
    public static void connectToDatabase()
    {
        if (db == null)
        {
            db = new Database();
        }
        else
        {
            // TODO create DB Error
            throw new Error("Database connection already established. " + 
                    "Cannot create a new connection until previous connection" +
                    " has been terminated.");
        }
    }
    
    /**
     * Tests to see if the current database is connected.
     * @return A code giving information on whether the test succeeds or not.
     * All possible return codes are {@link #DATABASE_NULL},
     * {@link #DATABASE_NOT_CONNECTED}, and {@link #DATABASE_CONNECTED}.
     */
    public static int databaseConnected()
    {
        if (db == null)
        {
            return DATABASE_NULL;
        }
        else if (!db.testConnection())
        {
            return DATABASE_NOT_CONNECTED;
        }
        else
        {
            return DATABASE_CONNECTED;
        }
    }
    
    /**
     * <p>Gets a {@link #Table} for testing purposes.</p>
     * <p>Note that all test tables are 5 columns long and all values are
     * strings. Please only add strings to the rows. Nothing else will work.
     * </p>
     * @return A test {@link #Table}
     */
    public static Table getTestTable()
    {
        int status = databaseConnected();
        if (status == DATABASE_CONNECTED)
        {
            return db.createDummyTable();
        }
        else if (status == DATABASE_NULL)
        {
            // TODO create DB Error
            throw new Error("Cannot get table. " + 
                    "Database connection not established.");
        }
        else if (status == DATABASE_NOT_CONNECTED)
        {
            // TODO create DB Error
            throw new Error("Cannot get table. " + 
                    "Database connection has been terminated.");
        }
        else
        {
            // TODO create DB Error
            throw new Error("Cannot get table. " + 
                    "Unexpected error occurred.");
        }
    }
    
    // class methods
    
    /**
     * Constructs Database. Empty constructor.
     */
     private Database() {}
    
    /**
     * Tests to see if the current database is connected.
     * @return true if connected, false if not.
     */
    private boolean testConnection()
    {
        // test result
        return true;
    }
    
    /**
     * Creates a table for testing.
     * @return
     */
    private Table createDummyTable()
    {
        Table dummy = new Table();
        return dummy;
    }
    
}
