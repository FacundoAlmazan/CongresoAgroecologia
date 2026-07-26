import { formatearFechaApi } from './fecha-api';

describe('formatearFechaApi', () => {
  it('formatea una fecha ISO', () => {
    expect(formatearFechaApi('2026-07-24T17:08:33.999488')).toBe('24/07/2026');
  });

  it('formatea un LocalDateTime serializado como arreglo', () => {
    expect(formatearFechaApi([2026, 7, 24, 17, 8, 33, 999488000])).toBe('24/07/2026');
  });

  it('muestra un marcador cuando la fecha no existe', () => {
    expect(formatearFechaApi(null)).toBe('—');
  });
});
