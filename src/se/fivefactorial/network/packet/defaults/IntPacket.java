package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class IntPacket extends Packet {

	private int i;

	public IntPacket(int i) {
		this.i = i;
	}

	@FactoryConstructor
	public IntPacket(Buffer buffer, List<Packet> packages) {
		i = buffer.getInt();
	}

	@Override
	protected void getLoad(Buffer buffer) {
		buffer.addInt(i);
	}

	@Override
	protected void getPackages(List<Packet> packages) {
	}

	public int getInt() {
		return i;
	}

}
