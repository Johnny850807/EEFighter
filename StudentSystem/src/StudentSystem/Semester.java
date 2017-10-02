package StudentSystem;

public class Semester {

	private int id;
	private int credit;
	
	public Semester(int id, int credit) {
		this.id = id;
		this.credit = credit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCredit() {
        return credit;
    }
	
}
