package isys1118.group1.server.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * <p>Represents a table of rows, used by the database.</p>
 * 
 * <p>Whenever you want to interact with the database, you need to use a table.
 * The table gives a full or partial list of rows in the database. Rows are
 * either added to the database or created to be added. Once work is done,
 * changes can be committed.
 * 
 * <p><b>Creator:</b><br />
 * Name: Luke Larobina<br />
 * Student Number: s3287121<br />
 * Email: s3287121@student.rmit.edu.au</p>
 *
 */
public class Table
{
    
    public static final int TEST_TABLE_LENGTH = 10;
    
    private final String tableName;
    protected final int numColumns;
    protected final ArrayList<String> columnNames;
    
    protected final ArrayList<Row> rows;
    
    /**
     * Creates a table object which represents a table in the database. Only for
     * testing.
     */
    protected Table()
    {
    	tableName = "test";
        numColumns = TEST_TABLE_LENGTH;
        columnNames = new ArrayList<String>(numColumns);
        rows = new ArrayList<Row>();
        
        // fill schema
        for (int i = 0; i < numColumns; i++)
        {
            columnNames.add(createRandomString(6));
        }
    }
    
    protected Table(String tableName, String[] names) throws Exception {
    	if (names.length <= 0) {
    		throw new Exception("Failed to create table: problem with table header");
    	}
    	this.tableName = tableName;
    	numColumns = names.length;
    	columnNames = new ArrayList<String>(numColumns);
    	rows = new ArrayList<Row>();
    	for (int i = 0; i < names.length; i++) {
    		columnNames.add(names[i]);
    	}
    }
    
    /**
     * Gets the index of the column with the given column name.
     * @param columnName
     * @return
     */
    public int getColumnIndex(String columnName)
    {
        return columnNames.indexOf(columnName);
    }
    
    /**
     * <p>Creates a new row for this table. The Row is linked to the table and
     * must comply to the table's schema.</p>
     * <p>All changes made to the new row will be updated to the database
     * once the table is committed.</p>
     * @param data
     * @return
     * @throws Exception 
     */
    public Row createNewRow(ArrayList<String> data)
    {
    	try {
    		Row r = new Row(this, data);
            rows.add(r);
            return r;
    	}
        catch (Exception e) {
        	System.out.println(e.getMessage());
        	return null;
        }
    }
    
    /**
     * <p>Creates a new row for this table. The Row is linked to the table and
     * must comply to the table's schema.</p>
     * <p>All changes made to the new row will be updated to the database
     * once the table is committed.</p>
     * @param data
     * @return
     * @throws Exception 
     */
    public Row createNewRow(String[] data) throws Exception
    {
    	try {
    		Row r = new Row(this, data);
            rows.add(r);
            return r;
    	}
        catch (Exception e) {
        	System.out.println(e.getMessage());
        	return null;
        }
    }
    
    /**
     * <p>Creates a new row for this table. The Row is linked to the table and
     * must comply to the table's schema.</p>
     * <p>This row is initialised with now data, and so the data needs to be
     * added to it later on</p>
     * <p>All changes made to the new row will be updated to the database
     * once the table is committed.</p>
     * @param data
     * @return
     */
    public Row createNewRow()
    {
        Row r = new Row(this);
        rows.add(r);
        return r;
    }
    
    /**
     * <p>Gets a row from the database using the given string query.</p>
     * <p>All changes made to the returned row will be updated to the database
     * once the table is committed.</p>
     * <p><b>NOTE: this only returns test rows for now. String is not required.
     * Just pass null.</b></p>
     * @param query
     * @return
     * @throws Exception 
     */
    @Deprecated
    public Row getRowFromTable(String query) throws Exception
    {
        String[] data = new String[numColumns];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = createRandomString((int) (6 + Math.random() * 6));
        }
        Row dummy = new Row(this, data);
        rows.add(dummy);
        return dummy;
    }
    
    /**
     * <p>Gets a row from the database using the given string query.</p>
     * <p>All changes made to the returned row will be updated to the database
     * once the table is committed.</p>
     * @param where
     * @return Row or null
     * @throws Exception 
     */
    public Row getRowEquals(String field, String value)
    {
    	for (Row r : rows) {
    		if (r.has(field)) {
				if (r.get(field).equals(value)) {
					return r;
				}
    		}
    	}
    	return null;
    }
    
    /**
     * <p>Gets all Rows from the database using the given string query.</p>
     * <p>All changes made to the returned rows will be updated to the database
     * once the table is committed.</p>
     * <p><b>NOTE: this only returns test rows for now. String is not required.
     * Just pass null.</b></p>
     * @param query
     * @return 
     * @throws Exception 
     */
    @Deprecated
    public Row[] getRowsFromTable(String query) throws Exception
    {
        int end = (new Random()).nextInt(5);
        Row[] allDummies = new Row[2 + end];
        for (int j = 0; j < 2 + end; j++)
        {
            String[] data = new String[numColumns];
            for (int i = 0; i < data.length; i++)
            {
                data[i] = createRandomString((int) (6 + Math.random() * 6D));
            }
            Row dummy = new Row(this, data);
            rows.add(dummy);
            allDummies[j] = dummy;
        }
        
        return allDummies;
    }
    
    /**
     * <p>Gets all Rows from the database using the given string query.</p>
     * <p>All changes made to the returned rows will be updated to the database
     * once the table is committed.</p>
     * @param query
     * @return ArrayList with all rows. Can be empty.
     */
    public ArrayList<Row> getRowsEqual(String field, String value)
    {
        ArrayList<Row> ret = new ArrayList<Row>();
        for (Row r : rows) {
    		if (r.has(field)) {
				if (r.get(field).equals(value)) {
					ret.add(r);
				}
    		}
    	}
    	return ret;
    }
    
    /**
     * Commits all changes to rows so far.
     * @return
     * @throws Exception 
     * @throws IOException 
     */
    public boolean commitChanges() throws Exception
    {

    	StringBuilder sb = new StringBuilder();
    	
    	// add all headers
    	for (int i = 0; i < numColumns; i++) {
    		sb.append(Database.toDBString(columnNames.get(i)));
    		if (i < numColumns - 1) {
    			sb.append(",");
    		}
    		else {
    			sb.append("\n");
    		}
    	}
    	
    	// add all rows
    	for (Row r : rows) {
    		for (int i = 0; i < numColumns; i++) {
        		sb.append(Database.toDBString(r.get(i)));
        		if (i < numColumns - 1) {
        			sb.append(",");
        		}
        		else {
        			sb.append("\n");
        		}
        	}
    	}
    	
    	FileWriter fw = null;
    	BufferedWriter bw = null;
    	try {
        	fw = new FileWriter(Database.getDatabase().getTableDir(tableName));
        	bw = new BufferedWriter(fw);
			
			// read each value and place it in a new row
        	bw.write(sb.toString());
			
		}
    	catch (Exception e) {
			throw e;
		}
    	finally {
    		if (bw != null) {
    			bw.close();
    		}
    		else if (fw != null) {
    			fw.close();
    		}
    	}
    	
        return true;
    }
    
    /**
     * Returns the length of the table (number of columns per row).
     * @return
     */
    public int getLength()
    {
        return numColumns;
    }
    
    /**
     * Returns a list of all column names, in order.
     * @return
     */
    public ArrayList<String> getColumnNames()
    {
        ArrayList<String> names = new ArrayList<String>(numColumns);
        names.addAll(columnNames);
        return names;
    }
    
    public Row getRowIndex(int index) {
    	return rows.get(index);
    }
    
    public int getNumRows() {
    	return rows.size();
    }
    
    private String createRandomString(int length)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append((char) (65 + Math.random() * 26D));
        }
        return sb.toString();
    }
    
}
