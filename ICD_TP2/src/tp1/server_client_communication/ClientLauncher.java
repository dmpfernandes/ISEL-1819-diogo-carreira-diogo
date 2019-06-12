package tp1.server_client_communication;

import java.util.Scanner;

public class ClientLauncher {
	ClienteUDP c;
	Scanner sc;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientLauncher cl = new ClientLauncher();
		cl.run();
		
	}
	
	public ClientLauncher() {
		c = new ClienteUDP();
	}
	
	public void run() {
		while(true) {
			
			
			System.out.println("Que desejas");
			sc = new Scanner(System.in);
			if(sc.hasNext()) {
				String pedido = sc.nextLine();
				c.atirar(pedido);
			}
			System.out.println("recebido: " + c.apanhar());
			System.out.println("recebido em multi: " + c.apanharMulti());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
