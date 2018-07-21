package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class StringPacket extends Packet {

	private String s;

	public StringPacket(String s) {
		this.s = s;
	}

	@FactoryConstructor
	public StringPacket(Buffer buffer) {
		s = buffer.getString();
	}

	@Override
	protected void getLoad(Buffer buffer) {
		buffer.addString(s);
	}

	@Override
	protected void getPackages(List<Packet> packages) {

	}

	public String getString() {
		return s;
	}

}
