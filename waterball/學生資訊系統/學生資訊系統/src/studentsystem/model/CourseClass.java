package studentsystem.model;

public class CourseClass implements Comparable<CourseClass>{

	private String number;

	private String classNumber;

	private Course course;

	private Professor professor;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	
	public String getCourseNumber(){
		return getCourse().getNumber();
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public int getCredit(){
		return getCourse().getCredit();
	}

	@Override
	public int compareTo(CourseClass o) {
		int thisCourseNumber = Integer.parseInt(getCourseNumber());
		int otherCourseNumber = Integer.parseInt(o.getCourseNumber());
		int thisClassNumber = Integer.parseInt(getClassNumber());
		int otherClassNumber = Integer.parseInt(o.getClassNumber());
		
		if (thisCourseNumber != otherCourseNumber)
			return thisCourseNumber - otherCourseNumber;
		return thisClassNumber - otherClassNumber;
	}
	
	@Override
	public String toString() {
		return String.format("Course Number %s, Class Number %s, Name %s - %s  , Credit %2d pt ", getCourseNumber(), getClassNumber(), getCourse().getName(), getCourse().getContent(), getCredit());
	}
	
}
