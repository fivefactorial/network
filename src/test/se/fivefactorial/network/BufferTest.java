package test.se.fivefactorial.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import se.fivefactorial.network.Buffer;

public class BufferTest {

	@Test
	public final void testEmptyBuffer() {
		Buffer buffer1 = new Buffer();
		assertEquals(buffer1, buffer1);
		Buffer buffer2 = new Buffer();
		assertEquals(buffer1, buffer2);
		assertEquals(buffer2, buffer1);
		buffer1.addArray(new byte[0]);
		assertEquals(buffer1, buffer2);
		assertEquals(buffer2, buffer1);
		buffer2.addArray(new byte[0]);
		assertEquals(buffer1, buffer2);
		assertEquals(buffer2, buffer1);
	}

	@Test
	public final void testBufferInt() {
		Buffer buffer1 = new Buffer();
		Buffer buffer2 = new Buffer();

		buffer1.addInt(5);
		assertNotEquals(buffer1, buffer2);
		buffer2.addInt(5);
		assertEquals(buffer1, buffer2);
	}
}
