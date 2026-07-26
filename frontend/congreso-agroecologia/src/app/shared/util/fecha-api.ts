export type FechaApi = string | number[] | null | undefined;

export function formatearFechaApi(fecha: FechaApi): string {
  if (Array.isArray(fecha)) {
    const [anio, mes, dia] = fecha;
    if ([anio, mes, dia].every(valor => Number.isInteger(valor))) {
      return `${String(dia).padStart(2, '0')}/${String(mes).padStart(2, '0')}/${anio}`;
    }
    return '—';
  }

  if (typeof fecha !== 'string' || !fecha.trim()) {
    return '—';
  }

  const coincidenciaIso = /^(\d{4})-(\d{2})-(\d{2})/.exec(fecha);
  return coincidenciaIso
    ? `${coincidenciaIso[3]}/${coincidenciaIso[2]}/${coincidenciaIso[1]}`
    : fecha;
}
