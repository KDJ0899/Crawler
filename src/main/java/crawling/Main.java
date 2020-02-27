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
		
		System.out.println(db.insert(status));
		
		list = db.getAll();
		
		System.out.println(list.get(0).getDate());

	}



}
