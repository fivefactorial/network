package se.fivefactorial.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import se.fivefactorial.network.packet.factory.PacketFactory;

public class Server extends Thread {

	private PacketFactory factory;
	private ServerSocket serverSocket;
	private List<Client> clients;

	public Server(int port, PacketFactory factory) throws IOException {
		this.factory = factory;
		serverSocket = new ServerSocket(port);
		clients = new ArrayList<>();
		start();
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				Client client = new Client(socket, factory);
				clients.add(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void interrupt() {
		close();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Server (");
		sb.append(isInterrupted() ? "closed" : "active");
		sb.append(")");
		for (Client client : clients) {
			sb.append("\n - ").append(client.toString());
		}
		return sb.toString();
	}

	public void close() {
		super.interrupt();
		try {
			serverSocket.close();
			for (Client client : clients) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}