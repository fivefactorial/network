package se.fivefactorial.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import se.fivefactorial.network.packet.ActionPacket;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.defaults.TransmissionPacket;
import se.fivefactorial.network.packet.factory.PacketFactory;

public class Client {

	private static int NUMBER_OF_CREATED_CLIENTS;
	private int clientID;

	private Connection connection;
	private PacketFactory factory;
	private Map<UUID, ActionPacket> inbox;

	public Client(Socket s, PacketFactory factory) {
		clientID = NUMBER_OF_CREATED_CLIENTS++;
		connection = new Connection(s);
		this.factory = factory;
		inbox = new HashMap<>();
		new Handler().start();
	}

	public Client(String host, int port, PacketFactory factory) throws UnknownHostException, IOException {
		this(new Socket(host, port), factory);
	}

	public synchronized UUID send(ActionPacket payload) {
		UUID uuid = new UUID();
		TransmissionPacket packet = new TransmissionPacket(uuid, payload);
		Buffer data = packet.getData();
		connection.send(data);
		return uuid;
	}

	public synchronized ActionPacket recieve(UUID uuid) {
		while (!inbox.containsKey(uuid)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return inbox.remove(uuid);
	}

	private synchronized void addResponse(UUID uuid, ActionPacket packet) {
		inbox.put(uuid, packet);
		notifyAll();
	}

	public int getClientID() {
		return clientID;
	}

	public synchronized boolean isClosed() {
		return connection.isClosed();
	}

	@Override
	public String toString() {
		return connection.toString();
	}

	private class Handler extends Thread {
		@Override
		public void run() {
			while (!connection.isClosed()) {
				Buffer buffer = connection.recieve();
				if (buffer == null)
					continue;
				Packet p = factory.create(buffer);
				if (p instanceof TransmissionPacket) {
					TransmissionPacket transmission = (TransmissionPacket) p;
					if (transmission.isResponse()) {
						UUID uuid = transmission.getID();
						ActionPacket response = transmission.getPacket();
						addResponse(uuid, response);
					} else {
						TransmissionPacket response = transmission.generateResponse();
						connection.send(response.getData());
					}
				} else {
					System.err.println("This was not a transmission!");
				}

			}
		}
	}

	public void close() {
		connection.close();
	}

}
