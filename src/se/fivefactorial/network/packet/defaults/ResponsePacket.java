package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.Buffer;
import se.fivefactorial.network.packet.ActionPacket;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class ResponsePacket extends ActionPacket {

	private Packet p;

	public ResponsePacket(Packet p) {
		this.p = p;
	}

	@FactoryConstructor
	public ResponsePacket(List<Packet> packages) {
		p = packages.get(0);
	}

	@Override
	public ActionPacket action() {
		return new NullPacket();
	}

	@Override
	protected void getLoad(Buffer buffer) {
	}

	@Override
	protected void getPackages(List<Packet> packages) {
		packages.add(p);
	}

	public Packet getPacket() {
		return p;
	}

}
