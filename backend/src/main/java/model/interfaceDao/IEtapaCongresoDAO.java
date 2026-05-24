package model.interfaceDao;

import java.util.List;
import model.EtapaCongreso;

public interface IEtapaCongresoDAO {

	List<EtapaCongreso> getEtapaCongresos();

	EtapaCongreso getByID(Long id);

	void modifyEtapaCongreso(EtapaCongreso a);

	boolean deleteByID(Long id);

	void saveEtapaCongreso(EtapaCongreso a);
	
}