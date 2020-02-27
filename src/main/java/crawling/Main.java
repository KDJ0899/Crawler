package crawling;

import java.util.List;

import db.DBConnector;
import dto.Status;

public class Main {

	public static void main(String[] args) {

		Status status = Crawler.clawling();

		System.out.println(status.getDate());
		
		DBConnector db = new DBConnector();
		
		List<Status> list;
		list = db.getAll();
		
		System.out.println(list.get(0).getDate());
		
		
		if(!status.getDate().equals(list.get(0).getDate()))
			System.out.println(db.insert(status));
		
	}

}
