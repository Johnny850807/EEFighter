package packets;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.Test;
import org.omg.CORBA.PRIVATE_MEMBER;

import model.Question;
import model.packet.Packets;
import model.packet.dto.*;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicRandomMapDirector;
import model.sprite.GameMap;
import model.sprite.SpriteName;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.words.PartOfSpeech;

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

	@Test
	public void testCheckAnswerRequest() throws IOException {
		CheckAnswerRequest dto = new CheckAnswerRequest((byte)1);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_REQ_CHECK_ANSWER);
		assertEquals(dto, Packets.parseCheckAnswerRequest(inputStream));
	}
	
	@Test
	public void testGameOverEvent() throws IOException {
		GameOverEvent dto = new GameOverEvent((byte)1, (byte)4, (byte)6);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_GAME_OVER);
		assertEquals(dto, Packets.parseGameOverEvent(inputStream));
	}

	@Test
	public void testGameStartEvent() throws IOException {
		GameMap gameMap = new BasicRandomMapDirector(new BasicMapBuilder()).buildMap();
		SpriteName[][] spriteNameMap = new SpriteName[gameMap.getHeight()][gameMap.getWidth()];
		for (int i = 0 ; i < gameMap.getHeight(); i ++)
			for (int j = 0; j < gameMap.getWidth(); j ++)
				spriteNameMap[i][j] = gameMap.getSprite(j, i).getSpriteName();
		
		GameStartEvent dto = new GameStartEvent(spriteNameMap, 
				new Point(1, 100), new Point(250, 74));
		
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_GAME_START);
		assertEquals(dto, Packets.parseGameStartEvent(inputStream));
	}
	
	@Test
	public void testMovementRequest() throws IOException {
		MovementRequest dto = new MovementRequest((byte)1, Direction.EAST, Status.MOVE);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_REQ_MOVEMENT);
		assertEquals(dto, Packets.parseMovementRequest(inputStream));
	}

	@Test
	public void testPickUpRequest() throws IOException {
		PickUpRequest dto = new PickUpRequest((byte)1);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_REQ_PICK_UP);
		assertEquals(dto, Packets.parsePickUpRequest(inputStream));
	}
	
	@Test
	public void testPlayerUpdatedEvent() throws IOException {
		PlayerUpdatedEvent dto = new PlayerUpdatedEvent((byte) 1, 
				new Player(new Point(178, 44), Direction.NORTH, Status.MOVE));
		
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_PLAYER_UPDATED);
		assertEquals(dto, Packets.parsePlayerUpdatedEvent(inputStream));
	}

	@Test
	public void testLettersPlacedUpdatedEvent() throws IOException {
		LettersUpdatedEvent dto = new LettersUpdatedEvent(createMockLetters());
		
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_LETTERS_PLACED_UPDATED);
		assertEquals(dto, Packets.parseLettersUpdatedEvent(inputStream));
	}
	
	@Test
	public void testPlayerLettersUpdatedEvent() throws IOException {
		PlayerLettersUpdatedEvent dto = new PlayerLettersUpdatedEvent(
				(byte)1, createMockLetters());
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_PLAYER_LETTERS_UPDATED);
		assertEquals(dto, Packets.parsePlayerLettersUpdatedEvent(inputStream));
	}
	
	private Letter[] createMockLetters(){
		return new Letter[]{new Letter(new Point(0, 10), SpriteName.A),
				new Letter(new Point(1, 10), SpriteName.B),
				new Letter(new Point(2, 102), SpriteName.C),
				new Letter(new Point(3, 103), SpriteName.D),
				new Letter(new Point(4, 104), SpriteName.E),
				new Letter(new Point(5, 105), SpriteName.G),
				new Letter(new Point(6, 106), SpriteName.T),
				new Letter(new Point(7, 107), SpriteName.I),
				new Letter(new Point(8, 108), SpriteName.P),
				new Letter(new Point(9, 109), SpriteName.Q),
				new Letter(new Point(10, 0), SpriteName.Z),
				new Letter(new Point(11, 0), SpriteName.Z)};
	}
	
	@Test
	public void testThrowLastestWordRequest() throws IOException {
		ThrowLastestWordRequest dto = new ThrowLastestWordRequest((byte)1);
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_REQ_THROW_LATEST_WORD);
		assertEquals(dto, Packets.parseThrowLastestWordRequest(inputStream));
	}

	@Test
	public void testNextQuestionEvent() throws IOException {
		NextQuestionEvent dto = new NextQuestionEvent(
				new Question(0x0FFFFFFF, "apple", "/v/a/b/c/d/s/e"
						, PartOfSpeech.N, "native Eurasian tree widely cultivated in many varieties for its firm rounded edible fruits"));
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_NEXT_QUESTION);
		assertEquals(dto, Packets.parseNextQuestionEvent(inputStream));
	}
	
	@Test
	public void testPlayVoiceEvent() throws IOException {
		PlayVoiceEvent dto = new PlayVoiceEvent("apple", "/v/a/b/c/d/s/e");
		DataInputStream inputStream = Packets.parse(dto).createDataInputStream();
		assertEquals(inputStream.readByte(), PID_EV_PLAY_VOICE);
		assertEquals(dto, Packets.parsePlayWordVoiceEvent(inputStream));
	}
}
