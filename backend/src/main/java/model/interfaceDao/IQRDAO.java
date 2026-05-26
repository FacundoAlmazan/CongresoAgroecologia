package model.interfaceDao;

import java.util.List;
import model.QR;

public interface IQRDAO extends IGenericDAO<QR> {
    List<QR> getQRs();
    void saveQR(QR entidad);
    QR modifyQR(QR entidad);
}
