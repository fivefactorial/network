package se.fivefactorial.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

	private Socket socket;

	private BufferQueue inbox;
	private BufferQueue outbox;

	public Connection(Socket socket) {
		this.socket = socket;
		inbox = new BufferQueue(socket);
		outbox = new BufferQueue(socket);
		new Reciever().start();
		new Sender().start();
	}

	public void send(Buffer buffer) {
		outbox.offer(buffer);
	}

	public Buffer recieve() {
		return inbox.poll();
	}

	@Override
	public String toString() {
		return socket.getInetAddress().toString();
	}

	public boolean isClosed() {
		return socket.isClosed();
	}

	public void close() {
		try {
			socket.close();
			inbox.threadDied();
			outbox.threadDied();
		} catch (IOException e) {
			System.err.printf("Tried to close socket, %s\n", e.getMessage());
		}
	}

	private class Reciever extends Thread {

		private InputStream is;

		@Override
		public void run() {
			try {
				is = socket.getInputStream();
				while (true) {
					byte[] byteLength = read(4);
					int length = Buffer.bytesToInt(byteLength);
					byte[] data = read(length);
					Buffer buffer = new Buffer(data);
					inbox.offer(buffer);
				}
			} catch (IOException e) {
				// e.printStackTrace();
			} finally {
				close();
			}
		}

		private byte read() throws IOException {
			int data = is.read();
			if (data == -1) {
				throw new IOException("Error when reading data from inputstream, reached EOF");
			}
			return (byte) data;
		}

		private byte[] read(int length) throws IOException {
			byte[] data = new byte[length];
			for (int i = 0; i < length; i++) {
				data[i] = read();
			}
			return data;
		}
	}

	private class Sender extends Thread {

		private OutputStream os;

		@Override
		public void run() {
			try {
				os = socket.getOutputStream();
				while (!socket.isClosed()) {
					Buffer buffer = outbox.poll();
					if (buffer == null)
						continue;
					int length = buffer.size();
					os.write(Buffer.intToBytes(length));
					os.write(buffer.toBytes());
					os.flush();
				}
			} catch (IOException e) {
				// e.printStackTrace();
			} finally {
				close();
			}
		}
	}
}
