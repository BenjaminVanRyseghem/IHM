package support;

import java.util.List;

import player.MPPlayerModel;

public class MPSearchThread extends Thread {
	String search;
	MPPlayerModel model;
	
	public MPSearchThread(String text, MPPlayerModel playerModel) {
		this.search = text;
		this.model = playerModel;
	}

	@Override
	public void run(){
		
		List<String[]> db = model.getDatabase();
		for(String[] info : db){
			
			// In order to avoid some exception
			// if the user type really fast
			Object timer = new Object();
			synchronized(timer){
				try {
					timer.wait(10);
				} catch (InterruptedException e) {
					return;
				}
			};
			
			for(String field : info){
				if(field.toLowerCase().contains(search.toLowerCase())){
					model.addInMusic(info);
					break;
				}
			}
		}
	}
	
}
