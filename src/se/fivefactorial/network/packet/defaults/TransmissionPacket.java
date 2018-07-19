package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.packet.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class TransmissionPacket extends Packet {

	private String id;
	private ActionPacket packet;

	public TransmissionPacket(String id, ActionPacket packet) {
		this.id = id;
		this.packet = packet;
	}

	@FactoryConstructor
	public TransmissionPacket(Buffer buffer, List<Packet> packets) {
		id = buffer.getString();
		packet = (ActionPacket) packets.get(0);
	}

	@Override
	protected void getLoad(Buffer buffer) {
		buffer.addString(id);
	}

	@Override
	protected void getPackages(List<Packet> packages) {
		packages.add(packet);
	}

	public Packet getPacket() {
		return packet;
	}
	
	public String getID() {
		return id;
	}

}