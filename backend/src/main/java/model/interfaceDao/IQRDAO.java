package model.interfaceDao;

import java.util.List;
import model.QR;

public interface IQRDAO {

	List<QR> getQRs();

	QR getByID(Long id);

	void modifyQR(QR a);

	boolean deleteByID(Long id);

	void saveQR(QR a);
	
}