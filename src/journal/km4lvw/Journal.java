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
//        ArrayList<Entry> entries = new ArrayList<>();
//        int counter = 1; //id's are 1 based in sqlite3.
//
//        Entry e = db.getEntry(counter++);
//        while (e != null)
//        {
//            entries.add(e);
//            System.out.println(e.getEntryTitle());
//            e = db.getEntry(counter++);
//        }
//
//        if (entries.size() <= 0)
//        {
//            entries = null;
//        }
//        return entries;
        return db.getEntries();
    }
    
    AbstractList<Entry> getParentEntries()
    {
//        ArrayList<Entry> entries = new ArrayList<>();
//        int counter = 1; //id's are 1 based in sqlite3.
//
//        Entry e = db.getEntry(counter++);
//        while (e != null)
//        {
//            entries.add(e);
//            System.out.println(e.getEntryTitle());
//            e = db.getEntry(counter++);
//        }
//
//        if (entries.size() <= 0)
//        {
//            entries = null;
//        }
//        return entries;
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
}
