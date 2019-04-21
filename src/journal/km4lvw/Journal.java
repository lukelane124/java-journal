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
import Database.Database;
import java.util.AbstractList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public class Journal 
{
    private ArrayList<Entry> entrys;	//All entries as part of this journal.
    private JournalDate creationDate;	//Journal Creation Date.
    private JournalDate lastUpdateDate;
    JournalDatabase db;
    private static Journal journal;
    private Journal()
    {
	   entrys = new ArrayList<>();
	   creationDate = new JournalDate();
	   lastUpdateDate = new JournalDate();
           db = JournalDatabase.getInstance(); 
    }
    
    static Journal getInstance()
    {
        if (journal == null)
        {
            journal = new Journal();
        }
	   return journal;
    }
    
    void addEntry(String title, String entry)
    {
        db.addEntry(title, entry);
    }
    
    void getEntries()
    {
        ResultSet rs = db.sqlQueryWResults("select * from entries");
        try 
        {
            while (rs.next())
            {
                System.out.println(rs.getString("entry_title"));
            }
        }
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
    }

    AbstractList<String> getTitles() {
	   AbstractList<String> ret = new ArrayList<>();
	   ResultSet rs = db.getTitles();
	   return ret;
    }
}
