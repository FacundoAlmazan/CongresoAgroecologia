import { Component, OnInit, ChangeDetectorRef} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Actividad } from '../../model/actividad.model';
import { ActividadService } from '../../service/actividad.service';

type ActividadFormData = {
  id?: number;
  titulo: string;
  descripcion: string;
  aula: string;
  fecha: string;
  horaInicio: string;
  horaFin: string;
  cronograma: { id: number };
};

@Component({
  selector: 'app-cronograma',
  imports: [FormsModule],
  templateUrl: './cronograma.html',
  styleUrl: './cronograma.css'
})
export class Cronograma implements OnInit {
  actividades: Actividad[] = [];

  modalAbierto = false;
  modoEdicion = false;
  cargando = false;
  error = '';

  // Temporal hasta implementar login/roles reales.
  esAdmin = true;

  actividadFormData: ActividadFormData = this.nuevaActividadVacia();

  constructor(
    private actividadService: ActividadService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarActividades();
  }

  get actividadesManiana(): Actividad[] {
    return this.actividades.filter(a => {
      const hora = this.horaComoString(a.horaInicio);
      return hora && hora < '12:00';
    });
  }

  get actividadesTarde(): Actividad[] {
    return this.actividades.filter(a => {
      const hora = this.horaComoString(a.horaInicio);
      return hora && hora >= '12:00';
    });
  }

  cargarActividades(): void {
    this.cargando = true;
    this.error = '';

    this.actividadService.listar().subscribe({
      next: data => {
        console.log('Actividades recibidas:', data);

        this.actividades = (data ?? [])
          .filter(a => !!a && !!a.titulo)
          .map(a => this.normalizarActividad(a));

        console.log('Actividades normalizadas:', this.actividades);
        console.log('Mañana:', this.actividadesManiana);
        console.log('Tarde:', this.actividadesTarde);

        this.cargando = false;

        this.cd.detectChanges();
      },
      error: err => {
        console.error('Error cargando actividades:', err);

        this.error = 'No se pudieron cargar las actividades.';
        this.actividades = [];
        this.cargando = false;

        this.cd.detectChanges();
      }
    });
  }

  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.error = '';
    this.actividadFormData = this.nuevaActividadVacia();
    this.modalAbierto = true;
  }

  abrirModalEditar(actividad: Actividad): void {
    this.modoEdicion = true;
    this.error = '';
    this.actividadFormData = {
      id: actividad.id,
      titulo: actividad.titulo ?? '',
      descripcion: actividad.descripcion ?? '',
      aula: actividad.aula ?? '',
      fecha: this.fechaComoInput(actividad.fecha),
      horaInicio: this.horaComoString(actividad.horaInicio),
      horaFin: this.horaComoString(actividad.horaFin),
      cronograma: actividad.cronograma ?? { id: 4 }
    };
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.error = '';
    this.actividadFormData = this.nuevaActividadVacia();
  }

  guardarActividad(): void {
    console.log('Guardar actividad:', this.actividadFormData);

    const actividad: Actividad = {
      id: this.actividadFormData.id,
      titulo: this.actividadFormData.titulo,
      descripcion: this.actividadFormData.descripcion,
      aula: this.actividadFormData.aula,
      fecha: this.fechaComoInput(this.actividadFormData.fecha),
      horaInicio: this.completarHora(this.actividadFormData.horaInicio),
      horaFin: this.completarHora(this.actividadFormData.horaFin),
      cronograma: { id: 3 } //por ahora queda hardcodeado esto, mas adelante debería ser cronograma: { id: this.cronogramaId }
    };

    console.log('Actividad enviada:', actividad);
    console.log('Actividad enviada JSON:', JSON.stringify(actividad));

    const request = this.modoEdicion && actividad.id
      ? this.actividadService.modificar(actividad.id, actividad)
      : this.actividadService.crear(actividad);

    request.subscribe({
      next: () => {
        this.cerrarModal();
        this.cargarActividades();
      },
      error: err => {
        console.error('Error guardando actividad:', err);
        this.error = err?.error?.message ?? 'No se pudo guardar la actividad.';
      }
    });
  }

  confirmarEliminar(actividad: Actividad): void {
    if (!actividad.id) return;

    const confirma = confirm(`¿Eliminar la actividad "${actividad.titulo}"?`);
    if (!confirma) return;

    this.actividadService.eliminar(actividad.id).subscribe({
      next: () => this.cargarActividades(),
      error: err => {
        console.error('Error eliminando actividad:', err);
        this.error = 'No se pudo eliminar la actividad.';
      }
    });
  }

  horaComoString(hora: unknown): string {
    if (!hora) return '';

    if (Array.isArray(hora)) {
      const horas = String(hora[0] ?? 0).padStart(2, '0');
      const minutos = String(hora[1] ?? 0).padStart(2, '0');
      return `${horas}:${minutos}`;
    }

    return String(hora).substring(0, 5);
  }

  fechaComoInput(fecha: unknown): string {
    if (!fecha) return '';

    if (Array.isArray(fecha)) {
      const anio = fecha[0];
      const mes = String(fecha[1] ?? 1).padStart(2, '0');
      const dia = String(fecha[2] ?? 1).padStart(2, '0');
      return `${anio}-${mes}-${dia}`;
    }

    return String(fecha).substring(0, 10);
  }

  private nuevaActividadVacia(): ActividadFormData {
    return {
      titulo: '',
      descripcion: '',
      aula: '',
      fecha: '',
      horaInicio: '',
      horaFin: '',
      cronograma: { id: 1 }
    };
  }

  private completarHora(hora: unknown): string {
    const horaString = this.horaComoString(hora);
    if (!horaString) return '';
    return horaString.length === 5 ? `${horaString}:00` : horaString;
  }

  private normalizarActividad(actividad: Actividad): Actividad {
    return {
      ...actividad,
      fecha: this.fechaComoInput(actividad.fecha),
      horaInicio: this.horaComoString(actividad.horaInicio),
      horaFin: this.horaComoString(actividad.horaFin),
      cronograma: actividad.cronograma ?? { id: 10 } // cuando se pueda crear el cronograma, reemplazar este valor por el real
    };
  }
}
