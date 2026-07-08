import { Component, Input, SimpleChanges } from '@angular/core';
import { NgClass } from '@angular/common';
import { RouterLink } from '@angular/router';

import { Trabajo } from '../../../model/trabajo.model';
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { TrabajoService } from '../../../service/trabajo.service';

@Component({
  selector: 'app-lie-card',
  imports: [RouterLink, NgClass],
  templateUrl: './lie-card.html',
  styleUrl: './lie-card.css',
})
export class LieCard {
  @Input() trabajo: Trabajo = {
    id: -1,
    titulo: '',
    passwordTrabajo: '',
    tipo: TipoTrabajo.RELATO_EXPERIENCIA,
    estado: EstadoTrabajo.BORRADOR,
    autores: [],
    ejesTematicos: [],
    asignaciones: [],
    evaluaciones: []
  };

  edit = false;
  enviar = false;
  texto = '';

  estilo = 'opacity-50 cursor-not-allowed';

  constructor(private trabajoService: TrabajoService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['trabajo'] && this.trabajo) {
      this.configEstado();
    }
  }

  enviarTrabajo(): void {
    if (!this.trabajo.id || this.trabajo.id <= 0) {
      console.error('No se puede enviar un trabajo sin id válido.');
      return;
    }

    this.trabajoService.enviar(this.trabajo.id).subscribe({
      next: trabajoActualizado => {
        console.log('Trabajo enviado:', trabajoActualizado);

        this.trabajo = {
          ...trabajoActualizado,
          autores: trabajoActualizado.autores ?? [],
          ejesTematicos: trabajoActualizado.ejesTematicos ?? [],
          asignaciones: trabajoActualizado.asignaciones ?? [],
          evaluaciones: trabajoActualizado.evaluaciones ?? [],
        };

        this.configEstado();
      },
      error: err => {
        console.error('No se pudo enviar trabajo. Intentá más tarde.', err);
        alert('No se pudo enviar el trabajo.');
      }
    });
  }

  configEstado(): void {
    this.edit = false;
    this.enviar = false;
    this.estilo = 'opacity-50 cursor-not-allowed';

    switch (this.trabajo.estado) {
      case EstadoTrabajo.EN_EVALUACION:
        this.texto = 'En revisión';
        break;

      case EstadoTrabajo.ACEPTADO_CON_CORRECCION:
        this.texto = 'Corregido';
        this.edit = true;
        this.enviar = true;
        this.estilo = '';
        break;

      case EstadoTrabajo.BORRADOR:
        this.texto = 'Borrador';
        this.edit = true;
        this.enviar = true;
        this.estilo = '';
        break;

      case EstadoTrabajo.ACEPTADO:
        this.texto = 'Aceptado';
        break;

      case EstadoTrabajo.ENVIADO:
        this.texto = 'Enviado';
        break;

      case EstadoTrabajo.RECHAZADO:
        this.texto = 'Rechazado';
        break;

      default:
        this.texto = 'Borrador';
        this.edit = true;
        this.enviar = true;
        this.estilo = '';
        break;
    }
  }
}