package factory;

import java.net.Socket;

import controller.EEFighter;
import controller.p2p.EEFighterP2PClient;

public class P2PClientAbstractFactory extends ReleasedComponentAbstractFactory{
	private Socket clientSocket; 
	
	
	public P2PClientAbstractFactory(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}


	@Override
	public EEFighter createEEFighter() {
		return new EEFighterP2PClient(clientSocket, this);
	}
}
