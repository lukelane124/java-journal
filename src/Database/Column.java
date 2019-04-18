/*
 * This software package was created by KM4LVW.
 * If you find it, use it wisely
 */

package Database;

import java.util.ArrayList;

/**
 *
 * @author Tommy Lane <km4lvw@km4lvw.com>
 */
public class Column 
{
    protected enum COLUMN_TYPES
    {
        TEXT,
        INTEGER,
        BLOB,
    }
    private final String column_name;
    private final Column.COLUMN_TYPES column_type;
    private final ArrayList<String> entries;
    
    protected Column(String name, Column.COLUMN_TYPES type)
    {
        this.column_name = name;
        this.column_type = type;
        entries = new ArrayList<>();
    }
    
    protected String getColumnName()
    {
        return column_name;
    }
    
    protected Column.COLUMN_TYPES getColumnType()
    {
        return column_type;
    }
    
    protected void addEntry(String entry_data)
    {
        entries.add(entry_data);
    }
    
    protected String[] getEntries()
    {
        return (String[]) entries.toArray();
    }
}
