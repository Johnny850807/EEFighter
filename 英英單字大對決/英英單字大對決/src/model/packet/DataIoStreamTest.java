package model.packet;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.junit.Before;
import org.junit.Test;

public class DataIoStreamTest {

	@Test
	public void test() throws Exception {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bao);
		out.writeInt(1);
		out.writeUTF("TEST English");
		out.writeByte((byte)1);
		out.writeShort(1);
		out.writeUTF("Test \n multiple \n lines");
		out.writeLong(10);
		out.writeLong(10);
		out.writeFloat(1.1f);
		out.writeUTF("測試中文");
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bao.toByteArray()));
		assertEquals(1, in.readInt());
		assertEquals("TEST English", in.readUTF());
		assertEquals(0X01, in.readByte());
		assertEquals(1, in.readShort());
		assertEquals("Test \n multiple \n lines", in.readUTF());
		assertEquals(10, in.readLong());
		assertEquals(10, in.readLong());
		assertEquals(1.1F, in.readFloat(), 0.0f);
		assertEquals("測試中文", in.readUTF());
	}

}
