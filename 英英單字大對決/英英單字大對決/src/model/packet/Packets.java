package model.packet;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import model.Question;
import model.packet.dto.AnswerCommitResultEvent;
import model.packet.dto.CheckAnswerRequest;
import model.packet.dto.GameOverEvent;
import model.packet.dto.GameStartEvent;
import model.packet.dto.Letter;
import model.packet.dto.MovementRequest;
import model.packet.dto.NextQuestionEvent;
import model.packet.dto.PickUpRequest;
import model.packet.dto.PlayVoiceEvent;
import model.packet.dto.Player;
import model.packet.dto.PlayerLettersUpdatedEvent;
import model.packet.dto.PlayerReadyRequest;
import model.packet.dto.PlayerUpdatedEvent;
import model.packet.dto.LettersUpdatedEvent;
import model.packet.dto.ThrowLastestWordRequest;
import model.sprite.SpriteName;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.words.PartOfSpeech;

public class Packets {
	/**
	 * PID = packet's id
	 * REQ = request from client
	 * EV = event broadcasted from server
	 */
	public interface PacketIds{
		public static final byte PID_REQ_PLAYER_READY = -1;
		public static final byte PID_REQ_MOVEMENT = -2;
		public static final byte PID_REQ_PICK_UP = -3;
		public static final byte PID_REQ_CHECK_ANSWER = -4;
		public static final byte PID_REQ_THROW_LATEST_WORD = -5;
	
		public static final byte PID_EV_GAME_START = 0;
		public static final byte PID_EV_NEXT_QUESTION = 1;
		public static final byte PID_EV_PLAY_VOICE = 2;
		public static final byte PID_EV_ANSWER_COMMIT_RESULT = 3;
		public static final byte PID_EV_PLAYER_LETTERS_UPDATED = 4;
		public static final byte PID_EV_LETTERS_PLACED_UPDATED = 5;
		public static final byte PID_EV_GAME_OVER = 6;
		public static final byte PID_EV_PLAYER_UPDATED = 7;
	}

	public static PlayerReadyRequest parsePlayerReadyRequest(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			return new PlayerReadyRequest(playerNo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static EEPacket parse(PlayerReadyRequest playerReadyRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_PLAYER_READY);
			packetWriter.writeByte(playerReadyRequest.playerNo);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static AnswerCommitResultEvent parseAnswerCommitResultEvent(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			boolean answerCorrect = inputStream.readBoolean();
			return new AnswerCommitResultEvent(playerNo, answerCorrect);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(AnswerCommitResultEvent answerCommitResultEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_ANSWER_COMMIT_RESULT);
			packetWriter.writeByte(answerCommitResultEvent.playerNo);
			packetWriter.writeBoolean(answerCommitResultEvent.answerCorrect);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static CheckAnswerRequest parseCheckAnswerRequest(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			return new CheckAnswerRequest(playerNo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(CheckAnswerRequest checkAnswerRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_CHECK_ANSWER);
			packetWriter.writeByte(checkAnswerRequest.playerNo);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static GameOverEvent parseGameOverEvent(DataInputStream inputStream){
		try {
			byte winnerNo = inputStream.readByte();
			byte player1Score = inputStream.readByte();
			byte player2Score = inputStream.readByte();
			return new GameOverEvent(winnerNo, player1Score, player2Score);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(GameOverEvent gameOverEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_GAME_OVER);
			packetWriter.writeByte(gameOverEvent.winnerNo);
			packetWriter.writeByte(gameOverEvent.player1Score);
			packetWriter.writeByte(gameOverEvent.player2Score);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static GameStartEvent parseGameStartEvent(DataInputStream inputStream){
		try {
			SpriteName[][] spriteNameMapMatrix = readSpriteNameMap(inputStream);
			Point player1Point = readPoint(inputStream);
			Point player2Point = readPoint(inputStream);
			return new GameStartEvent(spriteNameMapMatrix, player1Point, player2Point);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static SpriteName[][] readSpriteNameMap(DataInputStream inputStream) throws IOException{
		SpriteName[] spriteNames = SpriteName.values(); 
		byte mapHeight = inputStream.readByte();
		byte mapWidth = inputStream.readByte();
		SpriteName[][] map = new SpriteName[mapHeight][mapWidth];
		for(int i = 0; i < mapHeight; i ++)
			for(int j = 0 ; j < mapWidth; j ++)
				map[i][j] = spriteNames[inputStream.readByte()];
		return map;
	}

	public static EEPacket parse(GameStartEvent gameStartEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_GAME_START);
			writeSpirteNameMap(packetWriter, gameStartEvent.spriteNameMapMatrix);
			writePoint(packetWriter, gameStartEvent.player1Point.x, gameStartEvent.player1Point.y);
			writePoint(packetWriter, gameStartEvent.player2Point.x, gameStartEvent.player2Point.y);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
		
	private static void writeSpirteNameMap(PacketWriter packetWriter, SpriteName[][] map) throws IOException{
		packetWriter.writeByte(map.length);  //map's height
		packetWriter.writeByte(map[0].length);  //map's width
		for(int i = 0 ; i < map.length; i ++)
			for(int j = 0; j < map[0].length; j ++)
				packetWriter.writeByte(map[i][j].ordinal());
	}
	
	public static MovementRequest parseMovementRequest(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			Direction direction = Direction.values()[inputStream.readByte()];
			Status status = Status.values()[inputStream.readByte()];
			return new MovementRequest(playerNo, direction, status);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(MovementRequest movementRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_MOVEMENT);
			packetWriter.writeByte(movementRequest.playerNo);
			packetWriter.writeByte(movementRequest.direction.ordinal());
			packetWriter.writeByte(movementRequest.status.ordinal());
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PickUpRequest parsePickUpRequest(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			return new PickUpRequest(playerNo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(PickUpRequest pickUpResultEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_PICK_UP);
			packetWriter.writeByte(pickUpResultEvent.playerNo);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static LettersUpdatedEvent parseLettersUpdatedEvent(DataInputStream inputStream){
		try {
			Letter[] placedLetters = readLetters(inputStream);
			return new LettersUpdatedEvent(placedLetters);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Letter[] readLetters(DataInputStream inputStream) throws IOException{
		byte letterCount = inputStream.readByte();
		Letter[] placedLetters = new Letter[letterCount];
		SpriteName[] spriteNames = SpriteName.values();
		for (int i = 0; i < placedLetters.length; i ++)
		{
			Letter letter = new Letter();
			letter.point = readPoint(inputStream);
			letter.letterName = spriteNames[inputStream.readByte()];
			placedLetters[i] = letter;
		}
		return placedLetters;
	}
	
	public static EEPacket parse(LettersUpdatedEvent spritesUpdatedEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_LETTERS_PLACED_UPDATED);
			writeLetters(packetWriter, spritesUpdatedEvent.letters);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void writeLetters(PacketWriter packetWriter, Letter[] placedLetters) throws IOException {
		byte letterCount = (byte) placedLetters.length;
		packetWriter.writeByte(letterCount);
		for (Letter letter : placedLetters)
		{
			writePoint(packetWriter, letter.point.x, letter.point.y);
			packetWriter.writeByte(letter.letterName.ordinal());
		}
	}

	public static PlayerUpdatedEvent parsePlayerUpdatedEvent(DataInputStream inputStream) {
		try {
			byte playerNo = inputStream.readByte();
			Player player = readPlayer(inputStream);
			return new PlayerUpdatedEvent(playerNo, player);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(PlayerUpdatedEvent playerUpdatedEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_PLAYER_UPDATED);
			packetWriter.writeByte(playerUpdatedEvent.playerNo);
			writePlayer(packetWriter, playerUpdatedEvent.player);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PlayerLettersUpdatedEvent parsePlayerLettersUpdatedEvent(DataInputStream inputStream) {
		try {
			byte playerNo = inputStream.readByte();
			Letter[] letters = readLetters(inputStream);
			return new PlayerLettersUpdatedEvent(playerNo, letters);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(PlayerLettersUpdatedEvent playerLettersUpdatedEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_PLAYER_LETTERS_UPDATED);
			packetWriter.writeByte(playerLettersUpdatedEvent.playerNo);
			writeLetters(packetWriter, playerLettersUpdatedEvent.letters);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ThrowLastestWordRequest parseThrowLastestWordRequest(DataInputStream inputStream){
		try {
			byte playerNo = inputStream.readByte();
			return new ThrowLastestWordRequest(playerNo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(ThrowLastestWordRequest throwLastestWordRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_THROW_LATEST_WORD);
			packetWriter.writeByte(throwLastestWordRequest.playerNo);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static NextQuestionEvent parseNextQuestionEvent(DataInputStream inputStream){
		try {
			Question question = readQuestion(inputStream);
			return new NextQuestionEvent(question);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(NextQuestionEvent nextQuestionEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_NEXT_QUESTION);
			writeQuestion(packetWriter, nextQuestionEvent.question);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static Question readQuestion(DataInputStream inputStream) throws IOException {
		int number = inputStream.readInt();
	 	String word = inputStream.readUTF();
	 	String soundPath = inputStream.readUTF();
	 	PartOfSpeech partOfSpeech = PartOfSpeech.values()[inputStream.readByte()];
	 	String definition = inputStream.readUTF();
	 	return new Question(number, word, soundPath, partOfSpeech, definition);
	}
	
	public static void writeQuestion(PacketWriter packetWriter, Question question) throws IOException {
		packetWriter.writeInt(question.getNumber());
		packetWriter.writeUTF(question.getWord());
		packetWriter.writeUTF(question.getSoundPath());
		packetWriter.writeByte(question.getPartOfSpeech().ordinal());
		packetWriter.writeUTF(question.getDefinition());
	}
	
	public static PlayVoiceEvent parseEventPlayWordVoice(DataInputStream inputStream){
		try {
			String word = inputStream.readUTF();
			String soundPath = inputStream.readUTF();
			return new PlayVoiceEvent(word, soundPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static EEPacket parse(PlayVoiceEvent playVoiceEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_EV_PLAY_VOICE);
			packetWriter.writeUTF(playVoiceEvent.word);
			packetWriter.writeUTF(playVoiceEvent.soundPath);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writePoint(PacketWriter packetWriter, int x, int y) throws IOException{
		packetWriter.writeShort(x);
		packetWriter.writeShort(y);
	}
	
	public static Point readPoint(DataInputStream inputStream) throws IOException{
		return new Point(inputStream.readShort(), inputStream.readShort());
	}
	
	public static void writePlayer(PacketWriter packetWriter, Player player) throws IOException{
		writePoint(packetWriter, player.point.x, player.point.y);
		packetWriter.writeByte(player.direction.ordinal());
		packetWriter.writeByte(player.status.ordinal());
	}
	
	public static Player readPlayer(DataInputStream inputStream) throws IOException{
		Point point = readPoint(inputStream);
		Direction direction = Direction.values()[inputStream.readByte()];
		Status status = Status.values()[inputStream.readByte()];
		return new Player(point, direction, status);
	}
	/**
	* The class represents as a DataOutputStream wrapping on a ByteArrayOutputStream, which produces a byte array.
	* Notice that ByteArrayOutputStream needn't invoke close(), so neither PacketWriter. 
	**/
	private static class PacketWriter extends DataOutputStream{
		public PacketWriter() {
			super(new ByteArrayOutputStream());
		}
		
		public byte[] getBytes(){
			return ((ByteArrayOutputStream)out).toByteArray();
		}
	}
}

