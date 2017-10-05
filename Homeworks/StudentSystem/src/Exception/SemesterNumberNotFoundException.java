package Exception;

public class SemesterNumberNotFoundException extends RuntimeException{

	@Override
	public String getMessage() {
		return "找不到此學期";
	}
	
}
