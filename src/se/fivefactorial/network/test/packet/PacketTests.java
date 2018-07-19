package se.fivefactorial.network.test.packet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.defaults.IntPacket;
import se.fivefactorial.network.packet.defaults.NullPacket;
import se.fivefactorial.network.packet.defaults.StringPacket;
import se.fivefactorial.network.packet.factory.PacketFactory;

public class PacketTests {

	private PacketFactory pf = new PacketFactory();

	@Before
	public final void setup() {
		pf = new PacketFactory();
	}

	@Test
	public final void testNullPacket() {
		NullPacket n = new NullPacket();

		Class<?> c = pf.addPacket(NullPacket.class);
		assertNotNull(c);
		assertEquals(c, NullPacket.class);

		Packet p = pf.create(n.getData());
		assertEquals(p.getClass(), n.getClass());

		assertEquals(p.getData(), n.getData());
	}

	@Test
	public final void testIntPacket() {
		ArrayList<Integer> ints = new ArrayList<>();
		ints.add(0);
		ints.add(1);
		ints.add(5);
		ints.add(-1);
		ints.add(Integer.MAX_VALUE);
		ints.add(Integer.MIN_VALUE);

		Class<?> c = pf.addPacket(IntPacket.class);
		assertNotNull(c);
		assertEquals(c, IntPacket.class);

		for (int i : ints) {
			IntPacket ip = new IntPacket(i);
			Packet p = pf.create(ip.getData());
			assertEquals(p.getClass(), ip.getClass());
			assertEquals(p.getData(), ip.getData());

			IntPacket ip2 = (IntPacket) p;
			assertEquals(i, ip.getInt());
			assertEquals(i, ip2.getInt());
		}
	}

	@Test
	public final void testStringPacket() {
		ArrayList<String> strings = new ArrayList<>();
		strings.add("");
		strings.add("test");
		strings.add("tastatur");
		strings.add("едц342hfdjkdfhkjh3k2h4kj23hjkh 4jh3 kj43k2jhk4jh3jklj ");
		
		Class<?> c = pf.addPacket(StringPacket.class);
		assertNotNull(c);
		assertEquals(c, StringPacket.class);

		for (String s : strings) {
			StringPacket sp = new StringPacket(s);
			Packet p = pf.create(sp.getData());
			assertEquals(p.getClass(), sp.getClass());
			assertEquals(p.getData(), sp.getData());
			
			StringPacket sp2 = (StringPacket) sp;
			assertEquals(s, sp.getString());
			assertEquals(s, sp2.getString());
			
		}
	}

}
