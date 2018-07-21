package se.fivefactorial.network.packet;

import java.util.ArrayList;
import java.util.List;

import se.fivefactorial.network.Buffer;

public abstract class Packet {

	public final Buffer getData() {
		Buffer buffer = new Buffer();

		Buffer load = new Buffer();
		getLoad(load);

		ArrayList<Packet> packages = new ArrayList<>();
		getPackages(packages);

		buffer.addInt(hashName());
		buffer.addInt(load.size());
		buffer.addBuffer(load);
		buffer.addInt(packages.size());
		for (Packet packet : packages) {
			buffer.addBuffer(packet.getData());
		}
		return buffer;
	}

	public int hashName() {
		String name = getClass().getName();
		return hash(name);
	}

	public int hash(String name) {
		return name.hashCode();
	}

	protected abstract void getLoad(Buffer buffer);

	protected abstract void getPackages(List<Packet> packages);

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Packet) {
			Packet p = (Packet) obj;
			return p.getData().equals(getData());
		}
		return false;
	}
}
