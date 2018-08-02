package model.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Arrays;

public class EEPacket {
	private byte[] bytes;

	public EEPacket(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public DataInputStream createDataInputStream(){
		return new DataInputStream(new ByteArrayInputStream(bytes));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EEPacket other = (EEPacket) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		return true;
	}
	
	
}
