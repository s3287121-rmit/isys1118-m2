package isys1118.group1.server.database;

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
    
    protected final int numColumns;
    protected final ArrayList<String> columnNames;
    protected final ArrayList<String> columnTypes;
    
    protected final ArrayList<Row> rows;
    
    /**
     * Creates a table object which represents a table in the database. Only for
     * testing.
     */
    protected Table()
    {
        numColumns = TEST_TABLE_LENGTH;
        columnNames = new ArrayList<String>(numColumns);
        columnTypes = new ArrayList<String>(numColumns);
        rows = new ArrayList<Row>();
        
        // fill schema
        for (int i = 0; i < numColumns; i++)
        {
            columnNames.add(createRandomString(6));
            columnTypes.add("VARCHAR");
        }
    }
    
    protected Table(String[] names, String[] types) throws Exception {
    	if (names.length != types.length || names.length <= 0) {
    		throw new Exception("Failed to create table: problem with table header");
    	}
    	numColumns = names.length;
    	columnNames = new ArrayList<String>(numColumns);
    	columnTypes = new ArrayList<String>(numColumns);
    	rows = new ArrayList<Row>();
    	for (int i = 0; i < names.length; i++) {
    		columnNames.add(names[i]);
    		columnTypes.add(types[i]);
    	}
    }
    
    /**
     * Checks if all data given in the ArrayList fits the data table.
     * @param data
     * @return {@code true} if it fits, {@code false} if it does not.
     */
    public boolean checkIfDataFits(ArrayList<Object> data)
    {
        if (data.size() != numColumns)
        {
            return false;
        }
        
        // checks each object to see if their classes match.
        for (int i = 0; i < columnNames.size(); i++)
        {
            if (!data.get(i).getClass().equals(getType(i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if all data given in the ArrayList fits the data table.
     * @param data
     * @return {@code true} if it fits, {@code false} if it does not.
     */
    public boolean checkIfDataFits(Object[] data)
    {
        if (data.length != numColumns)
        {
            return false;
        }
        
        // checks each object to see if their classes match.
        for (int i = 0; i < columnNames.size(); i++)
        {
            if (!data[i].getClass().equals(getType(i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if the data given fits in the specified index.
     * @param index
     * @param data
     * @return {@code true} if it fits, {@code false} if it does not.
     */
    public boolean checkIfDataFits(int index, Object data)
    {
        return data.getClass().equals(getType(index));
    }
    
    /**
     * Checks if the data given fits in the specified index, using a column name
     * to find the data type.
     * @param columnName
     * @param data
     * @return {@code true} if it fits, {@code false} if it does not.
     */
    public boolean checkIfDataFits(String columnName, Object data)
    {
        int index = getColumnIndex(columnName);
        if (index < 0)
        {
            // TODO create DB Error
            throw new Error("No column exists with name: " + columnName);
        }
        return checkIfDataFits(index, data);
    }
    
    /**
     * <p>Gets the type of an object at column {@code index}. Currently only
     * supports:</p>
     * <ul>
     *     <li>INTEGER / INT</li>
     *     <li>SMALLINT</li>
     *     <li>VARCHAR</li>
     *     <li>BOOLEAN</li>
     *     <li>DECIMAL / DEC</li>
     *     <li>FLOAT</li>
     *     <li>NUMERIC</li>
     *     <li>CHARACTER / CHAR</li>
     * </ul>
     * 
     * @param index of column
     * @return A {@code Class} object representing the correct Java class of
     * the data type. Use this object to cast using {@link Class#cast(Object)}.
     */
    public Class<? extends Object> getType(int index)
    {
        String type = columnTypes.get(index);
        // Integer
        if (
                type.toUpperCase().equals("INTEGER") ||
                type.toUpperCase().equals("INT") ||
                type.toUpperCase().equals("SMALLINT"))
        {
            return Integer.class;
        }
        // String
        else if (
                type.toUpperCase().equals("VARCHAR") ||
                type.toUpperCase().equals("STRING"))
        {
            return String.class;
        }
        // Boolean
        else if (
                type.toUpperCase().equals("BOOLEAN"))
        {
            return Boolean.class;
        }
        // Double
        else if (
                type.toUpperCase().equals("DECIMAL") ||
                type.toUpperCase().equals("DEC") ||
                type.toUpperCase().equals("FLOAT") ||
                type.toUpperCase().equals("NUMERIC"))
        {
            return Double.class;
        }
        // Character
        else if (
                type.toUpperCase().equals("CHARACTER") ||
                type.toUpperCase().equals("CHAR"))
        {
            return Character.class;
        }
        else
        {
            // TODO create DB Error
            throw new Error("Type not supported.");
        }
    }
    
    /**
     * Gets a data type using a given column name. See {@link #getType(int)}.
     * @param columnName
     * @return See {@link #getType(int)}.
     */
    public Class<? extends Object> getType(String columnName)
    {
        int index = getColumnIndex(columnName);
        if (index < 0)
        {
            // TODO create DB Error
            throw new Error("No column exists with name: " + columnName);
        }
        return getType(index);
    }
    
    /**
     * <p>Gets the type of an object at column {@code index}. Currently only
     * supports:</p>
     * <ul>
     *     <li>INTEGER / INT</li>
     *     <li>SMALLINT</li>
     *     <li>VARCHAR</li>
     *     <li>BOOLEAN</li>
     *     <li>DECIMAL / DEC</li>
     *     <li>FLOAT</li>
     *     <li>NUMERIC</li>
     *     <li>CHARACTER / CHAR</li>
     * </ul>
     * 
     * @param index of column
     * @return A String object representing the correct Java class of
     * the data type. Use this object to cast using {@link Class#cast(Object)}.
     */
    public String getTypeName(int index)
    {
        String type = columnTypes.get(index);
        // Integer
        if (
                type.toUpperCase().equals("INTEGER") ||
                type.toUpperCase().equals("INT") ||
                type.toUpperCase().equals("SMALLINT"))
        {
            return "INTEGER";
        }
        // String
        else if (
                type.toUpperCase().equals("VARCHAR") ||
                type.toUpperCase().equals("STRING"))
        {
            return "STRING";
        }
        // Boolean
        else if (
                type.toUpperCase().equals("BOOLEAN"))
        {
            return "BOOLEAN";
        }
        // Double
        else if (
                type.toUpperCase().equals("DECIMAL") ||
                type.toUpperCase().equals("DEC") ||
                type.toUpperCase().equals("FLOAT") ||
                type.toUpperCase().equals("NUMERIC"))
        {
            return "DOUBLE";
        }
        // Character
        else if (
                type.toUpperCase().equals("CHARACTER") ||
                type.toUpperCase().equals("CHAR"))
        {
            return "CHAR";
        }
        else
        {
            // TODO create DB Error
            throw new Error("Type not supported.");
        }
    }
    
    /**
     * Gets a data type using a given column name. See {@link #getType(int)}.
     * @param columnName
     * @return See {@link #getType(int)}.
     */
    public String getTypeName(String columnName)
    {
        int index = getColumnIndex(columnName);
        if (index < 0)
        {
            // TODO create DB Error
            throw new Error("No column exists with name: " + columnName);
        }
        return getTypeName(index);
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
     */
    public Row createNewRow(ArrayList<String> data)
    {
        Row r = new Row(this, data);
        rows.add(r);
        return r;
    }
    
    /**
     * <p>Creates a new row for this table. The Row is linked to the table and
     * must comply to the table's schema.</p>
     * <p>All changes made to the new row will be updated to the database
     * once the table is committed.</p>
     * @param data
     * @return
     */
    public Row createNewRow(String[] data)
    {
        Row r = new Row(this, data);
        rows.add(r);
        return r;
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
     */
    @Deprecated
    public Row getRowFromTable(String query)
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
    public Row getRowWhere(String field, WhereTypes where, String value)
    {
    	for (Row r : rows) {
    		if (r.has(field)) {
    			if (where.equals(WhereTypes.EQUALS)) {
    				if (r.get(field).equals(value)) {
    					return r;
    				}
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
     */
    @Deprecated
    public Row[] getRowsFromTable(String query)
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
    public ArrayList<Row> getRowsWhere(String field, WhereTypes where, String value)
    {
        ArrayList<Row> ret = new ArrayList<Row>();
        for (Row r : rows) {
    		if (r.has(field)) {
    			if (where.equals(WhereTypes.EQUALS)) {
    				if (r.get(field).equals(value)) {
    					ret.add(r);
    				}
    			}
    		}
    	}
    	return ret;
    }
    
    /**
     * Commits all changes to rows so far.
     * @return
     */
    public boolean commitChanges()
    {
        System.out.println("Changes successfully committed.");
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
    
    /**
     * Returns a list of all column data types, in order.
     * @return
     */
    public ArrayList<String> getColumnTypes()
    {
        ArrayList<String> types = new ArrayList<String>(numColumns);
        types.addAll(columnTypes);
        return types;
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
