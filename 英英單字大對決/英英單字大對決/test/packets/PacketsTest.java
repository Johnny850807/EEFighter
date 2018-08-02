package packets;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.IOException;

import org.junit.Test;

import model.packet.Packets;
import model.packet.dto.*;
import static model.packet.Packets.PacketIds.*;

public class PacketsTest {

	@Test
	public void testPlayerReadyRequest() throws IOException {
		PlayerReadyRequest dto = new PlayerReadyRequest((byte)1);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_REQ_PLAYER_READY);
		assertEquals(dto, Packets.parsePlayerReadyRequest(inputStream));
	}

	@Test
	public void testAnswerCommitResultEvent() throws IOException {
		AnswerCommitResultEvent dto = new AnswerCommitResultEvent((byte)1, true);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_ANSWER_COMMIT_RESULT);
		assertEquals(dto, Packets.parseAnswerCommitResultEvent(inputStream));
	}

}
