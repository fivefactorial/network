package se.fivefactorial.network.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Buffer {
	private ArrayList<Byte> bytes;

	public Buffer() {
		bytes = new ArrayList<>();
	}

	public Buffer(byte[] array) {
		this();
		addArray(array);
	}

	public int size() {
		return bytes.size();
	}

	public void addByte(byte b) {
		bytes.add(b);
	}

	public void addArray(byte[] array) {
		for (byte b : array)
			bytes.add(b);
	}

	public byte[] getArray(int length) {
		byte[] array = new byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = bytes.remove(0);
		}
		return array;
	}

	public void addInt(int i) {
		byte[] array = ByteBuffer.allocate(4).putInt(i).array();
		addArray(array);
	}

	public int getInt() {
		return ByteBuffer.wrap(getArray(4)).getInt();
	}

	public void addBuffer(Buffer data) {
		for (Byte b : data.bytes)
			bytes.add(b);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Buffer) {
			return bytes.equals(((Buffer) obj).bytes);
		}
		return false;
	}

	@Override
	public String toString() {
		return bytes.toString();
	}

	public void addString(String s) {
		addInt(s.length());
		addArray(s.getBytes());
	}
	
	public String getString() {
		int length = getInt();
		byte[] data = getArray(length);
		return new String(data);
	}

}
