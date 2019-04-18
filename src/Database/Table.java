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
public class Table 
{
    private String table_name;
    private final ArrayList<Column> columns;
    
    protected Table(String tName)
    {
        table_name = tName;
        columns = new ArrayList<>();
    }
    
    protected void setTableName(String name)
    {
        this.table_name = name;
    }
    
    protected String getTableName()
    {
        return table_name;
    }
    
    protected void addEntry(String ColumnName, Column.COLUMN_TYPES type,
            String data)
    {
        boolean result = false;
        for (Column c : columns)
        {
            if (c.getColumnName().equals(ColumnName))
            {
                c.addEntry(data);
                result = true;
            }
            if (result == true)
            {
                break;
            }
        }
        if (result == false)
        {
            Column c = new Column(ColumnName, type);
            c.addEntry(data);
             columns.add(c);
        }
    }
    
    protected String[] getEntires(String ColumnName)
    {
        String[] ret = null;
        for (Column c : columns)
        {
            if (c.getColumnName().equals(ColumnName))
            {
                ret = c.getEntries();
                break;
            }
        }
        if (ret == null)
        {
            ret = new String[1];
            ret[0] = "";
        }
        return ret;
    }
}
