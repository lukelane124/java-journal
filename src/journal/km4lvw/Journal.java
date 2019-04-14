/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import java.util.ArrayList;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import org.sqlite.SQLiteConnection;
import org.sqlite.core.DB;

/**
 *
 * @author Lucas
 */
public class Journal 
{
    private String DATABASE_FILE = "test.db";
    Connection sqlCon = null;
    String createTable = "create table if not exists entries(entry_id INTEGER UNIQUE AUTOINCREMENT, entry_title TEXT, entry_content TEXT, data BLOB);";
    private void openJournalDatabase(String fileName) {
 
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
 
        try (Connection c = DriverManager.getConnection("test.db")) {
            if (c != null) {
                sqlCon = c;
            }
 
        } catch (SQLException e) {
            System.out.println("Unable to connect to test database.");
		  e.printStackTrace();
		  System.exit(100);
        }
    }
    
    private void sqlQuery(String query)
    {
	   if (sqlCon == null)
	   {
		  openJournalDatabase()
	   }
    }
    
    
    private ArrayList<Entry> entrys;	//All entries as part of this journal.
    private JournalDate creationDate;	//Journal Creation Date.
    private JournalDate lastUpdateDate;
    Journal()
    {
	   entrys = new ArrayList<>();
	   creationDate = new JournalDate();
	   lastUpdateDate = new JournalDate();
    }
    
    Journal ReadEntries()
    {
	  Journal j = new Journal();
	  
    }
    
    void AddEntry(Entry entry)
    {
	   entrys.add(entry);
    }
    //
    void addEntry(String entryName, String entryContent)
    {
	   entrys.add(new Entry(entryName, entryContent));
    }
    
    
}
