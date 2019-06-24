/*
 * This software package was created by KM4LVW.
 * If you find it, use it wisely
 */

package journal.km4lvw;
import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Tommy Lane <km4lvw@km4lvw.com>
 */
public class JournalDatabase extends Database 
{
    private static final String DATABASE_FILE = "jrnl.sqlite";
    protected static JournalDatabase localInstance;
    private JournalDatabase()
    {
        super(DATABASE_FILE);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS entries(\n" + 
                "id INTEGER PRIMARY KEY, entry_creation_date TEXT NOT NULL, \n" + 
                "entry_last_update_date TEXT NOT NULL, entry_title TEXT, \n" +
                "entry_content TEXT, entry_data BLOB);";
        executeSql(createTableSQL);
    }
    public static JournalDatabase getInstance()
    {
        if (localInstance == null)
        {
            localInstance = new JournalDatabase();
        }
        return  localInstance;
    }
    
    public void addEntry(String title, String entry)
    {
        String sqlString = "INSERT INTO entries(entry_title, entry_content," +
                "entry_creation_date, entry_last_update_date) " +
                "VALUES(?,?,?,?);";
        try (PreparedStatement pstmnt = sqlConnection.prepareStatement(sqlString))
        {
            pstmnt.setString(1, title);
            pstmnt.setString(2, entry);
            pstmnt.setString(3, (new SimpleDateFormat("dd-mm-yyyy-H:m:s:S").format(new Date())));
            pstmnt.setString(4, (new SimpleDateFormat("dd-mm-yyyy-H:m:s:S").format(new Date())));
            pstmnt.execute();
            pstmnt.close();
            System.out.println("the entry was added successfully to the database.");
        }
        catch (SQLException e)
        {
            System.out.println("Unable to add Entry");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Entry getEntry(int id)
    {
        ResultSet result = null;
        PreparedStatement pstmnt = null;
        String sqlString = "SELECT * " + 
                            "FROM entries " + 
                            "WHERE id=" + id;
        
        try
        {
            pstmnt = sqlConnection.prepareStatement(sqlString);
            
            result = pstmnt.executeQuery();
            
            return new Entry(result.getInt("id"), result.getString("entry_creation_date"), result.getString("entry_last_update_date"), result.getString("entry_title"), result.getString("entry_content"));
        }
        catch (SQLException e)
        {
            System.out.println("Unable to get Entry");
            System.out.println(e.getMessage());
        } finally{
            try{
                if(pstmnt != null) pstmnt.close();
                if(result != null) result.close();
                
            } catch(Exception ex){}
        }
        
        return null;
    }
    
    public Entry getLatestEntry()
    {
        ResultSet result = null;
        PreparedStatement pstmnt = null;
        String sqlString = "SELECT MAX(id) " + 
                            "FROM entries ;" ;
        
        try
        {
            pstmnt = sqlConnection.prepareStatement(sqlString);
            result = pstmnt.executeQuery();
            int rId = result.getInt("id");
            String rCdate = result.getString("entry_creation_date");
            String rLUdate = result.getString("entry_last_update_date");
            String rTitle = result.getString("entry_title");
            String rContent = result.getString("entry_content");
            return new Entry(rId, rCdate, rLUdate, rTitle, rContent);
        }
        catch (SQLException e)
        {
            System.out.println("Unable to get Entry");
            System.out.println(e.getMessage());
        } finally{
            try{
                if(pstmnt != null) pstmnt.close();
                if(result != null) result.close();
                
            } catch(Exception ex){}
        }
        
        return null;
    }

    ResultSet getTitles() {
	   ResultSet ret = null;
	   
	   try  
           {
                Statement test = sqlConnection.createStatement();
                PreparedStatement stmnt = sqlConnection.prepareStatement("SELECT * FROM entries;");
                ret = test.executeQuery("SELECT * FROM entries;");
		//ret = stmnt.executeQuery();
		stmnt.close();
	   } 
           catch (SQLException ex) 
           {
		  System.out.println("Unable to get entries from the entry database.");
		  System.out.println(ex.getMessage());
		  ex.printStackTrace();
	   }
	   
	   
	   return (ret);
    }
//    public ResultSet sqlQueryWResults(String sqlString)
    void appendEntry(Entry chosenEntry, String title, String entry) 
    {
        this.addEntry(title, entry);
        Entry e = this.getLatestEntry();
        int latestId = e.getId();
        chosenEntry.addChild(latestId);
        try (PreparedStatement pstnmt = sqlConnection.prepareStatement("UPDATE entries SET child=? WHERE id=?"))
        {
            pstnmt.setInt(1, latestId);
            pstnmt.setInt(2, chosenEntry.getId());
        }
        catch (SQLException e2)
        {
            System.out.println("Unable to add Entry");
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
    }
}
