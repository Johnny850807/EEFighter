package factory;

import java.net.ServerSocket;
import java.net.Socket;

import controller.EEFighter;
import controller.p2p.EEFighterP2PServer;

public class P2PServerAbstractFactory extends ReleasedComponentAbstractFactory{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	public P2PServerAbstractFactory(ServerSocket serverSocket, Socket clientSocket) {
		super();
		this.serverSocket = serverSocket;
		this.clientSocket = clientSocket;
	}
	
	@Override
	public EEFighter createEEFighter() {
		return new EEFighterP2PServer(serverSocket, clientSocket, this);
	}
}

