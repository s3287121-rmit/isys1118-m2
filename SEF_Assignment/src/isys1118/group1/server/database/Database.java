package isys1118.group1.server.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import isys1118.group1.shared.error.DatabaseException;

/**
 * <p>
 * Implements Database-related functions.
 * </p>
 * 
 * <p>
 * Used to access and modify information related to a database.
 * </p>
 * 
 * <p>
 * <b>NOTE: all functions in this class are placeholders ONLY!</b> These will be
 * used to properly connect to a database and get / set information at a later
 * date.
 * </p>
 * 
 * <p>
 * <b>Creator:</b><br />
 * Name: Luke Larobina<br />
 * Student Number: s3287121<br />
 * Email: s3287121@student.rmit.edu.au
 * </p>
 *
 */
public class Database {

	public static int DATABASE_NULL = 0x1001;
	public static int DATABASE_NOT_CONNECTED = 0x1002;
	public static int DATABASE_CONNECTED = 0x1003;

	private static Database db;

	public final File dbDir;

	/**
	 * Creates a database connection using a singleton method. This needs to be
	 * called before any database functions can work.
	 * 
	 * @throws Exception
	 */
	public static void connectToDatabase(String directory) throws DatabaseException {
		if (db == null) {
			db = new Database(directory);
			System.out.println("Database connected at: " +
					db.dbDir.getAbsolutePath());
		}
		else {
			DatabaseException de = new DatabaseException();
			de.setMessage("Database connection already established. " +
					"Cannot create a new connection until previous connection"
					+ " has been terminated.");
			throw de;
		}
	}

	/**
	 * Tests to see if the current database is connected.
	 * 
	 * @return A code giving information on whether the test succeeds or not.
	 *         All possible return codes are {@link #DATABASE_NULL},
	 *         {@link #DATABASE_NOT_CONNECTED}, and {@link #DATABASE_CONNECTED}.
	 */
	public static int databaseConnected() {
		if (db == null) {
			return DATABASE_NULL;
		}
		else if (!db.testConnection()) {
			return DATABASE_NOT_CONNECTED;
		}
		else {
			return DATABASE_CONNECTED;
		}
	}

	public static Database getDatabase() {
		return db;
	}

	/**
	 * <p>
	 * Gets a {@link #Table} for testing purposes.
	 * </p>
	 * <p>
	 * Note that all test tables are 5 columns long and all values are strings.
	 * Please only add strings to the rows. Nothing else will work.
	 * </p>
	 * 
	 * @return A test {@link #Table}
	 */
	public static Table getTestTable() throws DatabaseException {
		int status = databaseConnected();
		if (status == DATABASE_CONNECTED) {
			return db.createDummyTable();
		}
		else if (status == DATABASE_NULL) {
			DatabaseException de = new DatabaseException();
			de.setMessage("Cannot get table. " +
					"Database connection not established.");
			throw de;
		}
		else if (status == DATABASE_NOT_CONNECTED) {
			DatabaseException de = new DatabaseException();
			de.setMessage("Cannot get table. " +
					"Database connection has been terminated.");
			throw de;
		}
		else {
			DatabaseException de = new DatabaseException();
			de.setMessage("Cannot get table. " + "Unexpected error occurred.");
			throw de;
		}
	}

	// class methods

	/**
	 * Constructs Database. Empty constructor.
	 * 
	 * @throws Exception
	 */
	private Database(String dbPath) throws DatabaseException {
		dbDir = new File(dbPath);
		if (!dbDir.isDirectory()) {
			DatabaseException de = new DatabaseException();
			de.setMessage("Cannot find database at: " +
					dbDir.getAbsolutePath());
			throw de;
		}
	}

	/**
	 * Tests to see if the current database is connected.
	 * 
	 * @return true if connected, false if not.
	 */
	private boolean testConnection() {
		// test result
		return true;
	}

	/**
	 * Creates a table for testing.
	 * 
	 * @return
	 */
	@Deprecated
	private Table createDummyTable() {
		Table dummy = new Table();
		return dummy;
	}

	public Table getFullTable(String tableName)
			throws DatabaseException, IOException {
		File tableFile = getTableDir(tableName);
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(tableFile);
			br = new BufferedReader(fr);
			Table t = null;

			// read each value and place it in a new row
			while (br.ready()) {
				String line = br.readLine().trim();
				String[] split = line.split(",");

				// remove DB chars and change to Java / HTML chars
				for (int i = 0; i < split.length; i++) {
					split[i] = toJavaString(split[i]);
				}

				// do names if on first line
				if (t == null) {
					t = new Table(tableName, split);
				}
				// otherwise, add row as per usual
				else {
					t.createNewRow(split); // swallows creation of faulty lines.
				}
			}

			// no errors, so return table
			return t;

		} catch (Exception e) {
			DatabaseException de = new DatabaseException();
			de.setMessage(e.getMessage());
			throw de;
		} finally {
			if (br != null) {
				br.close();
			}
			else if (fr != null) {
				fr.close();
			}
		}
	}

	public File getTableDir(String tableName) {
		return new File(dbDir, tableName + ".data");
	}

	protected static String toJavaString(String dbString) {
		return dbString.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&cm;", ",").replaceAll("&smc;", ";")
				.replaceAll("&dqt;", "\"").replaceAll("&sqt;", "'").replaceAll("&amp;", "&");
	}

	protected static String toDBString(String javaString) {
		return javaString.replaceAll(";", "&smc;").replaceAll("&(?!smc;)", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll(",", "&cm;").replaceAll("\"", "&dqt;").replaceAll("'", "&sqt;");
	}

}
