/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Lucas
 */

class EntryEnums {
    enum Month
    {
	   JANUARY,
	   FEBRAUARY,
	   MARCH,
	   APRIL,
	   MAY,
	   JUNE,
	   JULY,
	   AUGUST,
	   SEPTEMBER,
	   OCTOBER,
	   NOVEMBER,
	   DECEMBER,
    }
    
    enum DayOfWeek
    {
	   MONDAY,
	   TUESDAY,
	   WEDNESDAY,
	   THURSDAY,
	   FRIDAY,
	   SATURDAY,
	   SUNDAY,
    }
}

class JournalDate
{
    EntryEnums.Month month;
    EntryEnums.DayOfWeek dow;
    int hour;//Military UTC
    int minute;
    int second;
    JournalDate()
    {
        Calendar date = Calendar.getInstance();
        hour = date.get(Calendar.HOUR_OF_DAY);
        minute = date.get(Calendar.MINUTE);
        second = date.get(Calendar.SECOND);
    }
}

public class Entry 
{
    private int id;
    private String entryCreationDate;
    private String lastEntryUpdateDate;
    private String entryTitle;
    private String entryContent;
    private int child;
    boolean LhasBlob;

    public Entry(int id, String entryCreationDate, String lastEntryUpdateDate, 
                    String entryTitle, String entryContent)
    {
        this.id = id;
        this.entryCreationDate = entryCreationDate;
        this.lastEntryUpdateDate = lastEntryUpdateDate;
        this.entryTitle = entryTitle;
        this.entryContent = entryContent;
        child = 0;
        LhasBlob = false;
    }

    public int getId()
    {
        return id;
    }

    public String getEntryCreationDate()
    {
        return entryCreationDate;
    }

    public String getLastEntryUpdateDate()
    {
        return lastEntryUpdateDate;
    }

    public String getEntryTitle()
    {
        return entryTitle;
    }

    public String getEntryContent()
    {
        return entryContent;
    }
    
    @Override
    public String toString()
    {
//        return "Entry: " + 
//                id + " | " +
//                entryTitle + " | " +
//                entryContent + " | " +
//                entryCreationDate + " | " +
//                lastEntryUpdateDate;
        return entryTitle;
    }

    void addChild(int Id) 
    {
        this.child = Id;
    }
    
    void deleteEntry()
    {
        Journal journal = Journal.getInstance();
        journal.deleteEntry(this.id, false);
    }
    
    void addBlob(byte[] bytes)
    {
        Journal journal = Journal.getInstance();
        journal.addBlob(this.id, bytes);
        this.LhasBlob = true;
    }
    
    byte[] getBlob()
    {
        Journal journal = Journal.getInstance();
        return journal.getBlob(this.id);
    }
    
    void deleteBlob(boolean realDelete)
    {
        Journal journal = Journal.getInstance();
        journal.deleteBlob(this.id, realDelete);
        LhasBlob = false;
    }
    
    public boolean hasBlob()
    {
        //return this.LhasBlob;
        Journal journal = Journal.getInstance();
        return journal.hasBlob(this.id);
    }
}
