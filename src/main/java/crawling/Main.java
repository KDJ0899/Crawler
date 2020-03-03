package crawling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import db.DBConnector;
import dto.Status;

public class Main {

	public static void main(String[] args) {
		

		Status status = Crawler.clawling();

		DBConnector db = new DBConnector();
		
		List<Status> list;
		list = db.getAll();
		String date = list.get(0).getDate();
		
		Date date1 = null;
		Date date2 = null;
		try {
		    DateFormat formatter ; 
		 
		    formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		    date1 = (Date)formatter.parse(status.getDate());
		    date2 = (Date)formatter.parse(date);
		} catch (Exception e) {}
		
		if(date1.getTime()!=date2.getTime())
			System.out.println(db.insert(status));
		
	}

}
