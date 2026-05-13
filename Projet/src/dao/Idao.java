package dao;
import java.util.List;
public interface Idao <T>{
	List<T> getAll();
	T findById (int id);
	void create(T obj);
	void delete(int id);
	void update(T obj);
}