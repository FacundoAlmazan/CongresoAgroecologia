import { Component, Input, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Trabajo } from '../../../model/trabajo.model';
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { RouterLink } from "@angular/router";


@Component({
  selector: 'app-lie-card',
  imports: [RouterLink],
  templateUrl: './lie-card.html',
  styleUrl: './lie-card.css',
})
export class LieCard {
  @Input() trabajo: Trabajo = {
  titulo: '',
  passwordTrabajo: '',
  tipo: {} as any,
  estado: {} as any, // Evita que la lectura de 'estado' devuelva undefined
  autores: [],
  ejesTematicos: [],
  asignaciones: [],
  evaluaciones: []
};
  
  edit:boolean=false;
  texto='';
  enviar=false;
  constructor(){
    
    console.log(this.trabajo)
  }


  // Método mapeador para resolver los estilos de Tailwind según el estado del paper
  configEstado(): void {
   

    switch (this.trabajo.estado) {
      case EstadoTrabajo.EN_EVALUACION:
        
          this.texto= 'En revisión';
          break;
      case EstadoTrabajo.ACEPTADO_CON_CORRECCION:
        this.texto= 'Corregido';
        this.edit=true;
        this.enviar=true;
          break;
      case EstadoTrabajo.BORRADOR:
        this.texto= 'Borrador';
        this.edit=true;
        this.enviar=true;
          break;
      case EstadoTrabajo.ACEPTADO:
        this.texto= 'Aceptado';
          break;
        case EstadoTrabajo.ENVIADO:
        this.texto= 'Enviado';
          break;
      case EstadoTrabajo.RECHAZADO:
        this.texto= 'Rechazado';
          break;
      default:
        this.texto= 'Undefined';
          break;
    }
  }

ngOnChanges(changes: SimpleChanges): void {
    // Validamos que el cambio corresponda a la propiedad 'trabajo' y que no sea nulo
    if (changes['trabajo'] && this.trabajo) {
      this.configEstado();
    }
  }
}


