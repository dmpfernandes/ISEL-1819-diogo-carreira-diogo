package tp1.client;

import java.io.ObjectInputStream;

public class MessageReceiver implements Runnable {
	
	Client c;
	String msg = null;
	
	public MessageReceiver(Client c) {
		this.c=c;
	}

	@Override
	public void run() {
		while(true) {
			 msg = c.receberMsg();
			 while(msg != null) {
				 try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
	}
	
	public String getMsg() {
		String m = msg;
		msg = null;
		return m;
	}
	
}
