package isys1118.group1.server.database;

import java.util.ArrayList;
import java.util.Random;

/**
 * <p>Represents a row in a table in the database.<p>
 * 
 * <p><b>Creator:</b><br />
 * Name: Luke Larobina<br />
 * Student Number: s3287121<br />
 * Email: s3287121@student.rmit.edu.au</p>
 *
 */
public final class Row
{

    private final Table fromTable;
    private final int length;
    protected final ArrayList<String> data;
    
    /**
     * Constructor using ArrayList.
     * @param parent
     * @param data
     */
    protected Row(Table parent, ArrayList<String> data)
    {
        fromTable = parent;
        length = fromTable.numColumns;
        this.data = new ArrayList<String>(length);
        setData(data);
    }
    
    /**
     * Constructor using Object array.
     * @param parent
     * @param data
     */
    protected Row(Table parent, String[] data)
    {
        fromTable = parent;
        length = fromTable.numColumns;
        this.data = new ArrayList<String>(length);
        setData(data);
    }
    
    /**
     * Constructor for empty row.
     * @param parent
     * @param data
     */
    protected Row(Table parent)
    {
        fromTable = parent;
        length = fromTable.numColumns;
        this.data = new ArrayList<String>(length);
    }
    
    /**
     * Sets the data of the entire row to the values given in {@code data}. The
     * length and types of {@code data} need to match those of the table's
     * schema.
     * @param data
     */
    public void setData(ArrayList<String> data)
    {
        if (data.size() != length)
        {
            // TODO create DB Error
            throw new Error("Attempting to add data of different length.");
        }
        this.data.clear();
        this.data.addAll(data);
    }

    /**
     * Sets the data of the entire row to the values given in {@code data}. The
     * length and types of {@code data} need to match those of the table's
     * schema.
     * @param data
     */
    public void setData(String[] data)
    {
        if (data.length != length)
        {
            // TODO create DB Error
            throw new Error("Attempting to add data of different length.");
        }
        this.data.clear();
        for (String o : data)
        {
            this.data.add(o);
        }
    }

    /**
     * Sets the data of a single row to the value given in {@code value}. The
     * type of {@code value} need to match the row's type, as specified in the
     * table's schema.
     * @param index Index of column to change.
     * @param value
     */
    public void setColumn(int index, String value)
    {
        data.set(index, value);
    }

    /**
     * Sets the data of a single row to the value given in {@code value}. The
     * type of {@code value} need to match the row's type, as specified in the
     * table's schema.
     * @param columnName Name of column to change.
     * @param value
     */
    public void setColumn(String columnName, String value)
    {
        int pos = fromTable.getColumnIndex(columnName);
        if (pos < 0)
        {
            // TODO create DB Error
            throw new Error("Table does not contain a column with name: "
                    + columnName);
        }
        setColumn(pos, value);
    }
    
    /**
     * Returns a field from the column whose name is given.
     * @param columnName
     * @return Data as object. Cast using {@link Table#getType(int)}.
     */
    public String get(String columnName)
    {
        int pos = fromTable.getColumnIndex(columnName);
        if (pos < 0)
        {
            // TODO create DB Error
            throw new Error("Table does not contain a column with name: "
                    + columnName);
        }
        return data.get(pos);
    }
    
    /**
     * Returns a field from the row denoted by the index.
     * @param index
     * @return Data as object. Cast using {@link Table#getType(int)}.
     */
    public Object get(int index)
    {
        return data.get(index);
    }
    
    public boolean has(String columnName) {
    	return fromTable.getColumnIndex(columnName) >= 0;
    }
    
    /**
     * Gets length of table.
     * @return length of table (number of columns).
     */
    public int getLength()
    {
        return length;
    }
    
}
