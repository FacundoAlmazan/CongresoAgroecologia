
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Trabajo } from '../../../model/trabajo.model';
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';


// Interfaz interna para estructurar los estilos dinámicos de los estados
interface EstadoConfig {
  texto: string;
  icono: string;
  claseBadge: string;
  claseCard: string;
  estiloIcono?: string;
}

@Component({
  selector: 'app-trabajo-card',
  imports: [CommonModule],
  templateUrl: './trabajo-card.html',
  styleUrl: './trabajo-card.css',
})
export class TrabajoCard {
  // Parámetro de entrada que recibirá la información desde el componente padre
  @Input() trabajo = {} as Trabajo;
  edit:boolean=false;

  // Supongamos que la propiedad 'evaluaciones' determina el número de comentarios
  get cantidadComentarios(): number {
    return this.trabajo.evaluaciones ? this.trabajo.evaluaciones.length : 0;
  }


  // Método mapeador para resolver los estilos de Tailwind según el estado del paper
  get configEstado(): EstadoConfig {
    const config:EstadoConfig={texto: '',
  icono: '',
  claseBadge: '',
  claseCard: '',

}
    const estadoActual = this.trabajo.estado;

    switch (estadoActual) {
      case EstadoTrabajo.EN_EVALUACION:
        return {
          texto: 'En revisión',
          icono: 'visibility',
          claseBadge: 'bg-amber-100 text-amber-800',
          claseCard: ''
        };
      case EstadoTrabajo.ACEPTADO_CON_CORRECCION:
        return {
          texto: 'Corregido',
          icono: 'visibility',
          claseBadge: 'bg-amber-100 text-amber-800',
          claseCard: ''
        };
      case EstadoTrabajo.BORRADOR:
        return {
          texto: 'Borrador',
        
          icono: 'warning',
          claseBadge: 'bg-orange-100 text-orange-800',
          claseCard: ''
        };
      case EstadoTrabajo.ACEPTADO:
        return {
          texto: 'Aceptado',
          icono: 'check_circle',
          claseBadge: 'bg-secondary-container text-on-secondary-container',
          claseCard: 'border-l-4 border-secondary',
          estiloIcono: '"FILL" 1'
        };
        case EstadoTrabajo.ENVIADO:
        return {
          texto: 'Enviado',
          icono: 'check_circle',
          claseBadge: 'bg-secondary-container text-on-secondary-container',
          claseCard: 'border-l-4 border-secondary',
          estiloIcono: '"FILL" 1'
        };
      case EstadoTrabajo.RECHAZADO:
        return {
          texto: 'Rechazado',
          icono: 'cancel',
          claseBadge: 'bg-error-container text-on-error-container',
          claseCard: ''
        };
      default:
        return {
          texto: 'Desconocido',
          icono: 'help',
          claseBadge: 'bg-gray-100 text-gray-800',
          claseCard: ''
        };
    }
  }

  // Métodos disparadores de acciones para los botones
  editar() {
    console.log('Editar trabajo:', this.trabajo.id);
  }

  descargar() {
    if (this.trabajo.estado?.toString().toUpperCase() !== 'RECHAZADO') {
      console.log('Descargando archivo:', this.trabajo.archivo);
    }
  }

  verComentarios() {
    console.log('Ver comentarios del trabajo:', this.trabajo.id);
  }
}
