package model.packet.dto;

public class PlayVoiceEvent {
	public String word;
	public String soundPath;
	
	public PlayVoiceEvent(String word, String soundPath) {
		super();
		this.word = word;
		this.soundPath = soundPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((soundPath == null) ? 0 : soundPath.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		PlayVoiceEvent other = (PlayVoiceEvent) obj;
		if (soundPath == null) {
			if (other.soundPath != null)
				return false;
		} else if (!soundPath.equals(other.soundPath))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	
}
