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
    
    Entry getEntry(int id)
    {
        return db.getEntry(id);
    }
    
    AbstractList<Entry> getEntries()
    {
        return db.getEntries();
    }
    
    AbstractList<Entry> getParentEntries()
    {
        return db.getParentEntries();
    }

    AbstractList<String> getTitles() {
	   AbstractList<String> ret = new ArrayList<>();
	   ResultSet rs = db.getTitles();
           try
           {
               while(rs.next())
               {
                   ret.add(rs.getString("entry_title"));
               }
               rs.close();
           }
           catch (SQLException e)
           {
               System.out.println("unable to call rs.next\n" + e.getMessage());
               e.printStackTrace();
           }
          
	   return ret;
    }

    void appendEntry(Entry chosenEntry, String title, String entry) 
    {
        db.appendEntry(chosenEntry, title, entry);
    }

    void deleteEntry(int Id, boolean b) {
        db.deleteEntry(Id, b);
    }
    
    void addBlob(int entryId, byte[] bytes)
    {
        db.addBlob(entryId, bytes);
    }
}
