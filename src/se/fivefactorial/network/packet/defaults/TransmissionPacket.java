package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.Buffer;
import se.fivefactorial.network.UUID;
import se.fivefactorial.network.packet.ActionPacket;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class TransmissionPacket extends Packet {

	private UUID uuid;
	private boolean response;
	private ActionPacket packet;

	public TransmissionPacket(UUID uuid, ActionPacket packet) {
		this(uuid, packet, false);
	}

	private TransmissionPacket(UUID uuid, ActionPacket packet, boolean response) {
		this.uuid = uuid;
		this.response = response;
		this.packet = packet;
	}

	@FactoryConstructor
	public TransmissionPacket(Buffer buffer, List<Packet> packets) {
		uuid = new UUID(buffer.getString());
		packet = (ActionPacket) packets.get(0);
		response = ((BooleanPacket) packets.get(1)).getBoolean();
	}

	@Override
	protected void getLoad(Buffer buffer) {
		buffer.addString(uuid.toString());
	}

	@Override
	protected void getPackages(List<Packet> packages) {
		packages.add(packet);
		packages.add(new BooleanPacket(response));
	}

	public TransmissionPacket generateResponse() {
		ActionPacket response = packet.action();
		return new TransmissionPacket(uuid, response, true);
	}

	public boolean isResponse() {
		return response;
	}

	public ActionPacket getPacket() {
		return packet;
	}

	public UUID getID() {
		return uuid;
	}

}