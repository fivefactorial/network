package se.fivefactorial.network;

import java.util.LinkedList;

public class BufferQueue {

	private LinkedList<Buffer> queue;

	public BufferQueue() {
		queue = new LinkedList<>();
	}

	public synchronized void offer(Buffer buffer) {
		queue.add(buffer);
		notifyAll();
	}

	public synchronized Buffer poll() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.removeFirst();
	}
}
