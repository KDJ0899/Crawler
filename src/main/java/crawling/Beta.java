package crawling;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dto.Status;

public class Beta {
	public static Status clawling(String date) {
		String url = "https://www.cdc.go.kr";//크롤링할 url지정
		String board = "/board/board.es?mid=a20501000000&bid=0015";
        Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다
        Status status = null;
        Element aLink,span,element;
        
        String title="코로나바이러스감염증-19 국내 발생 현황("+date+", 0시 기준)";
        
		try {

			doc = Jsoup.connect(url+board).get();

		} catch (IOException e) {

			e.printStackTrace();

		}
		if (doc != null) {
			Elements elements = doc.select("div#listView"); 
			Iterator<Element> nowStatus = null;
		    Iterator<Element> ie1 = elements.select("ul").iterator();
		   
		    while(ie1.hasNext()) {
		    	nowStatus = ie1.next().select("li.title").iterator();
		    	element = nowStatus.next();
		    	span = element.selectFirst("span");
		    	if(title.equals(span.text())) {
		    		board = element.selectFirst("a").attr("href");
		    		System.out.println(board);
		    		try {

		    			doc = Jsoup.connect(url+board).get();
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    		elements = doc.select("table tbody");
	    			ie1 = elements.select("tr").iterator();
	    			ie1.next();
	    			ie1.next();
	    			ie1.next();
    				nowStatus = ie1.next().select("td").iterator();
    				
    				nowStatus.next();
    				nowStatus.next();
    				
    				int quarantinedPatient =Integer.parseInt(nowStatus.next().text().replace(",", ""));
    		    	int treatedPatient = Integer.parseInt(nowStatus.next().text().replace(",", ""));
    		    	nowStatus.next();
    		    	int deceasedPerson = Integer.parseInt(nowStatus.next().text().replace(",", ""));
    		    	nowStatus.next();
    		    	int inspecting = Integer.parseInt(nowStatus.next().text().replace(",", ""));
    				
    		    	status = Status.builder()
    		    			.quarantinedPatient(quarantinedPatient)
    		    			.treatedPatient(treatedPatient)
    		    			.deceasedPerson(deceasedPerson)
    		    			.inspecting(inspecting)
    					.build();
		    		
		    		break;
		    	}
		    }
		}
        return status;
	}
}
