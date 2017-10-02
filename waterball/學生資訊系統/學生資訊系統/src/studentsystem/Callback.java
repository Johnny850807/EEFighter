package studentsystem;

public interface Callback<T> {

	public void onQueryFinish(T t);
	public void onError(Exception err);

}
