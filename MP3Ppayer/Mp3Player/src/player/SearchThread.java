package player;

import java.util.List;

public class SearchThread extends Thread {
	String search;
	PlayerModel model;
	public SearchThread(String text, PlayerModel playerModel) {
		this.search = text;
		this.model = playerModel;
	}

	public void run(){
		
		List<String[]> db = model.getDatabase();
		for(String[] info : db){
			try {
				Object timer = new Object();
				synchronized(timer){timer.wait(1000);};
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(String field : info){
				if(field.toLowerCase().contains(search.toLowerCase())){
					model.addInMusic(info);
					break;
				}
			}
		}
	}
	
}
