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
    private String title;
    private ArrayList<String> entry;
    private JournalDate jdate;
    
    
    
    public Entry(String entryName, String entryContent)
		  {
			 title = entryName;
			 entry = new ArrayList<>();
			 entry.add(entryContent);
			 jdate = new JournalDate();			 
		  }
		  
		  String getTitle()
		  {
			 return this.title;
		  }
		  
		  String[] getEntries()
		  {
			 return (String[])this.entry.toArray();
		  }
		  
		  void updateEntry(String updatedEntry)
		  {
			 this.entry.add(updatedEntry);
		  }
		  
		  
}
