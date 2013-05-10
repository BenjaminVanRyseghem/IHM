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
