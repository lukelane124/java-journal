/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import java.util.ArrayList;
import java.sql.*;
import java.util.Collection;
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
    private static final String DATABASE_FILE = "jdbc:sqlite:test.db";
    Connection sqlCon = null;
    String createTable = "create table if not exists entries(entry_id INTEGER UNIQUE AUTOINCREMENT, entry_title TEXT, entry_content TEXT, data BLOB);";
    private void openJournalDatabase(String fileName) {
 
        String url = "jdbc:sqlite:" + fileName;
 
        try (Connection c = DriverManager.getConnection(fileName)) {
            if (c != null) {
                sqlCon = c;
            }
 
        } catch (SQLException e) {
            System.out.println("Unable to connect to test database.");
		  e.printStackTrace();
		  System.exit(100);
        }
	   try (Statement stmnt = sqlCon.createStatement())
	   {
		  stmnt.execute(createTable);
	   }
	   catch(SQLException e)
	   {
		  System.out.println("Unable to create Table entries.");
		  e.printStackTrace();
	   }
    }
    
    private ResultSet sqlQuery(String query)
    {
	   ResultSet ret = null;
	   if (sqlCon == null)
	   {
		  openJournalDatabase(DATABASE_FILE);
	   }
	   try (Statement stmnt = sqlCon.createStatement())
	   {
		  ret = stmnt.executeQuery(query);
	   }
	   catch(SQLException e)
	   {
		  System.out.println("\nUnable to execute statement.");
		  e.printStackTrace();
	   }
	   return ret;
    }
    
    private void sqlReadEntriesFromDatabase()
    {
	   
	   ResultSet sqlResult = sqlQuery("select * from * in entries");
	   if (sqlResult != null)
	   {
		  // TODO: Add update of entrys.
		  System.out.println("sqlResult was not null!!");
	   }
	   
    }
    
    private ArrayList<Entry> entrys;	//All entries as part of this journal.
    private JournalDate creationDate;	//Journal Creation Date.
    private JournalDate lastUpdateDate;
    private static Journal journal = new Journal();
    private Journal()
    {
	   entrys = new ArrayList<>();
	   creationDate = new JournalDate();
	   lastUpdateDate = new JournalDate();
    }
    
    static Journal ReadEntries()
    {
	   journal.sqlReadEntriesFromDatabase();
	   //this.entrys.addAll(entries.);
	   return journal;
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
