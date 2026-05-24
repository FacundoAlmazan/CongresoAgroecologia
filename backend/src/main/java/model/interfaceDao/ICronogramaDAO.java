package model.interfaceDao;

import java.util.List;
import model.Cronograma;

public interface ICronogramaDAO {

	List<Cronograma> getCronogramas();

	Cronograma getByID(Long id);

	void modifyCronograma(Cronograma a);

	boolean deleteByID(Long id);

	void saveCronograma(Cronograma a);
	
}