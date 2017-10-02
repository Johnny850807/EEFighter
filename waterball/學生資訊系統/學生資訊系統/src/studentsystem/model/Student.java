package studentsystem.model;

public class Student {

	private int id;

	private String account;

	private String password;

	private String name;

	private CreditFeeType creditFeeType;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public CreditFeeType getCreditFeeType() {
		return creditFeeType;
	}

	public void setCreditFeeType(CreditFeeType creditFeeType) {
		this.creditFeeType = creditFeeType;
	}


	
	
}
