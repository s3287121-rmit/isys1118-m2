package isys1118.group1.server.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    
    private final File dbDir;
    
    /**
     * Creates a database connection using a singleton method. This needs to be
     * called before any database functions can work.
     * @throws Exception 
     */
    public static void connectToDatabase() throws Exception
    {
        if (db == null)
        {
            db = new Database("./db/");
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
     * @throws Exception 
     */
     private Database(String dbPath) throws Exception {
    	 dbDir = new File("./db/");
    	 if (!dbDir.isDirectory()) {
    		 throw new Exception("Cannot find database!");
    	 }
     }
    
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
    
    public Table getFullTable(String tableName) throws IOException {
    	File tableFile = new File(dbDir, tableName + ".data");
    	BufferedReader br = null;
    	FileReader fr = null;
    	boolean namesDone = false;
    	boolean typesDone = false;
    	
    	try {
			fr = new FileReader(tableFile);
			br = new BufferedReader(fr);
			Table t = new Table();
			
			// read each value and place it in a new row
			while (br.ready()) {
				String line = br.readLine().trim();
				String[] split = line.split(",");
				for (int i = 0; i < split.length; i++) {
					split[i] = toJavaString(split[i]);
				}
				Row r = t.
			}
		}
    	catch (FileNotFoundException e) {
			throw e;
		}
    	finally {
    		if (br != null) {
    			br.close();
    		}
    		else if (fr != null) {
    			fr.close();
    		}
    	}
    }
    
    private String toJavaString(String dbString) {
    	return dbString.replaceAll("&lt;", "<")
    				   .replaceAll("&gt;", "<")
    				   .replaceAll("&amp;", "&")
    				   .replaceAll("&cm;", ",")
    				   .replaceAll("&smc;", ";")
    				   .replaceAll("&dqt;", "\"")
    				   .replaceAll("&sqt;", "'");
    }
    
    private String toDBString(String javaString) {
    	return javaString.replaceAll("<", "&lt;")
    					 .replaceAll(">", "&gt;")
    					 .replaceAll("&", "&amp;")
    					 .replaceAll("'", "&cm;")
    					 .replaceAll(";", "&smc;")
    					 .replaceAll("\"", "&dqt;")
    					 .replaceAll("'", "&sqt;");
    }
    
}
