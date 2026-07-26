-- Migración del flujo de inscripciones.
-- Ejecutar sobre la base `jyaa` al desplegar el flujo, aunque Hibernate haya
-- creado las columnas: hbm2ddl=update no amplía un ENUM existente en MySQL.

DELIMITER $$

DROP PROCEDURE IF EXISTS agregar_columna_si_falta$$
CREATE PROCEDURE agregar_columna_si_falta(
    IN nombre_tabla VARCHAR(64),
    IN nombre_columna VARCHAR(64),
    IN definicion_columna VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.COLUMNS
        WHERE TABLE_SCHEMA = DATABASE()
          AND TABLE_NAME = nombre_tabla
          AND COLUMN_NAME = nombre_columna
    ) THEN
        SET @ddl = CONCAT(
            'ALTER TABLE `', nombre_tabla,
            '` ADD COLUMN `', nombre_columna, '` ',
            definicion_columna
        );
        PREPARE sentencia FROM @ddl;
        EXECUTE sentencia;
        DEALLOCATE PREPARE sentencia;
    END IF;
END$$

DELIMITER ;

CALL agregar_columna_si_falta('inscripciones', 'fechaRegistro', 'DATETIME(6) NULL');
CALL agregar_columna_si_falta('inscripciones', 'montoAbonado', 'DOUBLE NULL');
CALL agregar_columna_si_falta('inscripciones', 'diferenciaMonto', 'DOUBLE NULL');
CALL agregar_columna_si_falta('inscripciones', 'comentarioRevision', 'VARCHAR(2000) NULL');
CALL agregar_columna_si_falta('inscripciones', 'comprobanteContenido', 'LONGTEXT NULL');
CALL agregar_columna_si_falta('inscripciones', 'comprobanteAcreditacionContenido', 'LONGTEXT NULL');
CALL agregar_columna_si_falta('qrs', 'imagen', 'LONGTEXT NULL');

ALTER TABLE inscripciones
    MODIFY COLUMN estado
    ENUM('APROBADO', 'OBSERVADO', 'PENDIENTE', 'RECHAZADO') NOT NULL;

UPDATE inscripciones
SET fechaRegistro = COALESCE(fechaRegistro, TIMESTAMP(fechaPago), CURRENT_TIMESTAMP(6)),
    montoAbonado = COALESCE(
        montoAbonado,
        CASE WHEN efectivo = TRUE THEN 0 ELSE monto END
    ),
    diferenciaMonto = COALESCE(
        diferenciaMonto,
        GREATEST(
            0,
            monto - CASE WHEN efectivo = TRUE THEN 0 ELSE monto END
        )
    );

DROP PROCEDURE agregar_columna_si_falta;
