package se.fivefactorial.network;

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

	public void addBoolean(boolean b) {
		bytes.add(b ? (byte) 1 : (byte) 0);
	}

	public boolean getBoolean() {
		byte b = bytes.get(0);
		return !(b == 0);
	}

	public void addInt(int i) {
		byte[] array = intToBytes(i);
		addArray(array);
	}

	public int getInt() {
		return bytesToInt(getArray(4));
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

	public byte[] toBytes() {
		byte[] data = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			data[i] = bytes.get(i);
		}
		return data;
	}

	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] intToBytes(int i) {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

}
