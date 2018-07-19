package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.packet.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class NullPacket extends Packet {

	@FactoryConstructor
	public NullPacket() {

	}

	@Override
	protected void getLoad(Buffer buffer) {
	}

	@Override
	protected void getPackages(List<Packet> packages) {
	}

}
