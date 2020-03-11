package crawling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import db.DBConnector;
import dto.Status;

public class Main {

	public static void main(String[] args) {
		
		DBConnector db = new DBConnector();
		
		List<Status> list;
		list = db.getAll();
		String date = list.get(0).getDate();
		LocalDateTime date1 = null;
		try {
		    date1=LocalDateTime.parse(date,
		    	    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")); 
		    date1 =date1.plusDays(1);
		    date= date1.getMonthValue()+"월 "+(date1.getDayOfMonth())+"일";
		    
		    System.out.println(date);
		} catch (Exception e) {System.out.println(e);}
		
		Beta.clawling(date);
	}

}
