package se.fivefactorial.network;

import java.net.Socket;
import java.util.LinkedList;

public class BufferQueue {

	private LinkedList<Buffer> queue;
	private Socket socket;

	public BufferQueue(Socket socket) {
		queue = new LinkedList<>();
		this.socket = socket;
	}

	public synchronized void offer(Buffer buffer) {
		queue.add(buffer);
		notifyAll();
	}

	public synchronized void threadDied() {
		notifyAll();
	}

	public synchronized Buffer poll() {
		if (queue.isEmpty() && !socket.isClosed()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (queue.peek() != null)
			return queue.removeFirst();
		return null;
	}
}
