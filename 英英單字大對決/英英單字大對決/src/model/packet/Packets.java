package model.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import model.packet.dto.AnswerCommitResultEvent;
import model.packet.dto.CheckAnswerRequest;
import model.packet.dto.GameOverEvent;
import model.packet.dto.GameStartEvent;
import model.packet.dto.MovementRequest;
import model.packet.dto.NextQuestionEvent;
import model.packet.dto.PickUpRequest;
import model.packet.dto.PlayVoiceEvent;
import model.packet.dto.PlayerLettersUpdatedEvent;
import model.packet.dto.PlayerReadyRequest;
import model.packet.dto.SpritesUpdatedEvent;
import model.packet.dto.ThrowLastestWordRequest;

public class Packets {
	/**
	 * PID = packet's id
	 * REQ = request from client
	 * EV = event broadcasted from server
	 */
	public interface PacketIds{
		public static final byte PID_REQ_PLAYER_READY = -1;
		public static final byte PID_REQ_MOVING = -2;
		public static final byte PID_REQ_PICK_UP = -3;
		public static final byte PID_REQ_COMMIT_ANSWER = -4;
		public static final byte PID_REQ_THROW_LATEST_WORD = -5;
	
		public static final byte PID_EV_GAME_START = 0;
		public static final byte PID_EV_NEXT_QUESTION = 1;
		public static final byte PID_EV_PLAY_VOICE = 2;
		public static final byte PID_EV_ANSWER_COMMIT_RESULT = 3;
		public static final byte PID_EV_PLAYER_LETTERS_UPDATED = 4;
		public static final byte PID_EV_POSITION_UPDATED = 5;
		public static final byte PID_EV_GAME_OVER = 6;
	}

	public static PlayerReadyRequest parsePlayerReadyRequest(DataInputStream inputStream){
		return null;
	}
	
	public static EEPacket parse(PlayerReadyRequest playerReadyRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			packetWriter.writeByte(playerReadyRequest.playerNo);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static AnswerCommitResultEvent parseAnswerCommitResultEvent(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(AnswerCommitResultEvent answerCommitResultEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static CheckAnswerRequest parseCheckAnswerRequest(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(CheckAnswerRequest checkAnswerRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static GameOverEvent parseGameOverEvent(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(GameOverEvent gameOverEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static GameStartEvent parseGameStartEvent(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(GameStartEvent gameStartEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static MovementRequest parseMovementRequest(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(MovementRequest movementRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PickUpRequest parsePickUpResultEvent(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(PickUpRequest pickUpResultEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SpritesUpdatedEvent parseSpritesUpdatedEvent(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(SpritesUpdatedEvent spritesUpdatedEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PlayerLettersUpdatedEvent parsePlayerLettersUpdated(DataInputStream inputStream) {
		
		return null;
	}

	public static EEPacket parse(PlayerLettersUpdatedEvent playerLettersUpdatedEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ThrowLastestWordRequest parseThrowLastestWordRequest(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(ThrowLastestWordRequest throwLastestWordRequest){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static NextQuestionEvent parseEventNextQuestion(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(NextQuestionEvent nextQuestionEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PlayVoiceEvent parseEventPlayWordVoice(DataInputStream inputStream){
		return null;
	}

	public static EEPacket parse(PlayVoiceEvent playVoiceEvent){
		try {
			PacketWriter packetWriter = new PacketWriter();
			packetWriter.writeByte(PacketIds.PID_REQ_COMMIT_ANSWER);
			return new EEPacket(packetWriter.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	private static class PacketWriter extends DataOutputStream{
		public PacketWriter() {
			super(new ByteArrayOutputStream());
		}
		
		public byte[] getBytes(){
			return ((ByteArrayOutputStream)out).toByteArray();
		}
	}
}

