package se.fivefactorial.network.packet.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.fivefactorial.network.packet.Buffer;
import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.defaults.IntPacket;
import se.fivefactorial.network.packet.defaults.NullPacket;
import se.fivefactorial.network.packet.defaults.StringPacket;

public class PacketFactory {

	private Map<Integer, Class<? extends Packet>> packets;

	public PacketFactory() {
		packets = new HashMap<>();

		addPacket(NullPacket.class);
		addPacket(IntPacket.class);
		addPacket(StringPacket.class);
	}

	public Class<? extends Packet> addPacket(Class<? extends Packet> packet) {
		int validConstructors = 0;
		for (Constructor<?> constructor : packet.getConstructors()) {
			for (Annotation annotation : constructor.getAnnotations()) {
				if (annotation.annotationType() == FactoryConstructor.class) {
					validConstructors++;
				}
			}
		}
		if (validConstructors == 0) {
			throw new PacketFactoryException("Lacking constructor with the FactoryConstructor annotation");
		} else if (validConstructors > 1) {
			throw new PacketFactoryException(
					"Too many constructors with the FacotryConstructor annotation, only one accepted");
		}
		int hash = packet.getName().hashCode();
		Class<? extends Packet> old = packets.put(hash, packet);
		return old;
	}

	public Packet create(Buffer buffer) {
		int type = buffer.getInt();

		ArrayList<Param> params = new ArrayList<>();

		Buffer data = new Buffer(buffer.getArray(buffer.getInt()));
		List<Packet> list = new ArrayList<>();
		int listsize = buffer.getInt();
		for (int i = 0; i < listsize; i++) {
			list.add(create(buffer));
		}
		params.add(new FixedParam(data));
		params.add(new FixedParam(list));

		Class<? extends Packet> target = packets.get(type);
		Constructor<?> c = null;
		main: for (Constructor<?> constructor : target.getConstructors()) {
			for (Annotation annotation : constructor.getAnnotations()) {
				if (annotation.annotationType() == FactoryConstructor.class) {
					c = constructor;
					break main;
				}
			}
		}
		Parameter[] parameters = c.getParameters();
		Object[] args = new Object[parameters.length];
		int i = 0;
		for (Parameter param : parameters) {
			for (Param p : params) {
				if (p.is(param.getType())) {
					args[i++] = p.getInstance();
				}
			}
		}
		try {
			return (Packet) c.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new PacketFactoryException(e.getMessage());
		}
	}
}
