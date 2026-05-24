package model.interfaceDao;

import java.util.List;

import model.Circular;

public interface ICircularDAO {

	List<Circular> getCirculares();
	Circular getByID(Long id);
	void modifyCircular(Circular a);
	boolean deleteByID(Long id);
	void saveCircular(Circular a);
}
