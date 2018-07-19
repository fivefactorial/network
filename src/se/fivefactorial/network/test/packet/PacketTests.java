package se.fivefactorial.network.test.packet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import se.fivefactorial.network.packet.Packet;
import se.fivefactorial.network.packet.defaults.ActionPacket;
import se.fivefactorial.network.packet.defaults.IntPacket;
import se.fivefactorial.network.packet.defaults.NullPacket;
import se.fivefactorial.network.packet.defaults.ResponsePacket;
import se.fivefactorial.network.packet.defaults.StringPacket;
import se.fivefactorial.network.packet.defaults.TransmissionPacket;
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

		int n = 0;
		for (int i : ints) {
			n++;
			IntPacket ip = new IntPacket(i);
			Packet p = pf.create(ip.getData());
			assertEquals(p.getClass(), ip.getClass());
			assertEquals(p.getData(), ip.getData());

			IntPacket ip2 = (IntPacket) p;
			assertEquals(i, ip.getInt());
			assertEquals(i, ip2.getInt());
		}
		assertEquals(ints.size(), n);
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

		int n = 0;
		for (String s : strings) {
			n++;
			StringPacket sp = new StringPacket(s);
			Packet p = pf.create(sp.getData());
			assertEquals(p.getClass(), sp.getClass());
			assertEquals(p.getData(), sp.getData());

			StringPacket sp2 = (StringPacket) sp;
			assertEquals(s, sp.getString());
			assertEquals(s, sp2.getString());
		}
		assertEquals(strings.size(), n);
	}

	@Test
	public final void testResponsePacket() {
		ArrayList<Packet> packages = new ArrayList<>();
		packages.add(new IntPacket(0));
		packages.add(new StringPacket("fds"));
		packages.add(new NullPacket());

		Class<?> c = pf.addPacket(ResponsePacket.class);
		assertNotNull(c);
		assertEquals(c, ResponsePacket.class);

		int n = 0;
		for (Packet packet : packages) {
			n++;
			ResponsePacket rp = new ResponsePacket(packet);

			Packet p = pf.create(rp.getData());

			assertEquals(p.getClass(), rp.getClass());
			assertEquals(p.getData(), rp.getData());

			ResponsePacket rp2 = (ResponsePacket) p;
			assertEquals(packet, rp.getPacket());
			assertEquals(packet, rp2.getPacket());
		}
		assertEquals(packages.size(), n);
	}

	@Test
	public final void testTransmissionPacket() {
		Class<?> c = pf.addPacket(TransmissionPacket.class);
		assertNotNull(c);
		assertEquals(c, TransmissionPacket.class);

		String id = "jeg er et tastatur!";
		Packet load = new NullPacket();
		ActionPacket ap = new ResponsePacket(load);
		TransmissionPacket tp = new TransmissionPacket(id, ap);

		Packet p = pf.create(tp.getData());

		assertEquals(p.getClass(), tp.getClass());
		assertEquals(p.getData(), tp.getData());

		assertEquals(id, tp.getID());
		assertEquals(ap, tp.getPacket());
		TransmissionPacket tp2 = (TransmissionPacket) p;
		assertEquals(id, tp2.getID());
		assertEquals(ap, tp2.getPacket());
	}

}
