package se.fivefactorial.network.packet.defaults;

import java.util.List;

import se.fivefactorial.network.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.factory.FactoryConstructor;

public class BooleanPacket extends Packet{

	private boolean b;
	
	public BooleanPacket(boolean b) {
		this.b = b;
	}
	
	@FactoryConstructor
	public BooleanPacket(Buffer buffer) {
		b = buffer.getBoolean();
	}
	
	@Override
	protected void getLoad(Buffer buffer) {
		buffer.addBoolean(b);
	}

	@Override
	protected void getPackages(List<Packet> packages) {
		
	}
	
	public boolean getBoolean() {
		return b;
	}

}
