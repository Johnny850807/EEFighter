package studentsystem.model.modelview;

import java.util.List;

import studentsystem.model.CourseClass;

public class ClassesAndCreditSum {
	private List<CourseClass> classes;
	private int creditFeeSum;
	
	public ClassesAndCreditSum(List<CourseClass> classes, int creditFeeSum) {
		this.classes = classes;
		this.creditFeeSum = creditFeeSum;
	}
	
	public List<CourseClass> getClasses() {
		return classes;
	}
	public void setClasses(List<CourseClass> classes) {
		this.classes = classes;
	}
	public int getCreditSum() {
		return creditFeeSum;
	}
	public void setCreditFeeSum(int creditFeeSum) {
		this.creditFeeSum = creditFeeSum;
	}
	
	
}
