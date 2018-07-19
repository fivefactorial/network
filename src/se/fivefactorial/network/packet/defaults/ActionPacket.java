package se.fivefactorial.network.packet.defaults;

import se.fivefactorial.network.packet.Packet;

public abstract class ActionPacket extends Packet {

	public abstract ActionPacket action();
}
