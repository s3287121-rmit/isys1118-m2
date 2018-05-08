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
    protected final ArrayList<Object> data;
    
    /**
     * Constructor using ArrayList.
     * @param parent
     * @param data
     */
    protected Row(Table parent, ArrayList<Object> data)
    {
        fromTable = parent;
        length = fromTable.numColumns;
        this.data = new ArrayList<Object>(length);
        setData(data);
    }
    
    /**
     * Constructor using Object array.
     * @param parent
     * @param data
     */
    protected Row(Table parent, Object[] data)
    {
        fromTable = parent;
        length = fromTable.numColumns;
        this.data = new ArrayList<Object>(length);
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
        this.data = new ArrayList<Object>(length);
    }
    
    /**
     * Sets the data of the entire row to the values given in {@code data}. The
     * length and types of {@code data} need to match those of the table's
     * schema.
     * @param data
     */
    public void setData(ArrayList<Object> data)
    {
        if (data.size() != length)
        {
            // TODO create DB Error
            throw new Error("Attempting to add data of different length.");
        }
        if (fromTable.checkIfDataFits(data))
        {
            this.data.clear();
            this.data.addAll(data);
        }
        else
        {
            // TODO create DB Error
            throw new Error("Data did not match schema.");
        }
    }

    /**
     * Sets the data of the entire row to the values given in {@code data}. The
     * length and types of {@code data} need to match those of the table's
     * schema.
     * @param data
     */
    public void setData(Object[] data)
    {
        if (data.length != length)
        {
            // TODO create DB Error
            throw new Error("Attempting to add data of different length.");
        }
        if (fromTable.checkIfDataFits(data))
        {
            this.data.clear();
            for (Object o : data)
            {
                this.data.add(o);
            }
        }
        else
        {
            // TODO create DB Error
            throw new Error("Data did not match schema.");
        }
    }

    /**
     * Sets the data of a single row to the value given in {@code value}. The
     * type of {@code value} need to match the row's type, as specified in the
     * table's schema.
     * @param index Index of column to change.
     * @param value
     */
    public void setColumn(int index, Object value)
    {
        if (fromTable.checkIfDataFits(index, value))
        {
            data.set(index, value);
        }
        else
        {
            // TODO create DB Error
            throw new Error("Trying to add wrong type of data to column: "
                    + index);
        }
    }

    /**
     * Sets the data of a single row to the value given in {@code value}. The
     * type of {@code value} need to match the row's type, as specified in the
     * table's schema.
     * @param columnName Name of column to change.
     * @param value
     */
    public void setColumn(String columnName, Object value)
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
    public Object get(String columnName)
    {
        int pos = fromTable.getColumnIndex(columnName);
        if (pos < 0)
        {
            // TODO create DB Error
//          throw new Error("Table does not contain a column with name: "
//                  + columnName);
            pos = (new Random()).nextInt(length);
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
    
    /**
     * Gets length of table.
     * @return length of table (number of columns).
     */
    public int getLength()
    {
        return length;
    }
    
}
