import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Trabajo } from '../../model/trabajo.model';
import { EstadoTrabajo } from '../../model/EstadoTrabajo.enum.model';
import { LieCard } from './lie-card/lie-card';
import { TrabajoService } from '../../service/trabajo.service';

@Component({
  selector: 'app-panel-autor',
  imports: [RouterLink, LieCard],
  templateUrl: './panel-autor.html',
  styleUrl: './panel-autor.css',
})
export class PanelAutor implements OnInit {
  listadoTrabajos: Trabajo[] = [];

  cargando = false;
  mensajeError = '';

  constructor(
    private trabajoService: TrabajoService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarTrabajosDesdeServidor();
  }

  cargarTrabajosDesdeServidor(): void {
    this.cargando = true;
    this.mensajeError = '';
    this.listadoTrabajos = [];

    this.trabajoService.listar().subscribe({
      next: data => {
        console.log('Trabajos recibidos del servidor:', data);

        this.listadoTrabajos = (data ?? []).map(trabajo => ({
          ...trabajo,
          autores: trabajo.autores ?? [],
          ejesTematicos: trabajo.ejesTematicos ?? [],
          asignaciones: trabajo.asignaciones ?? [],
          evaluaciones: trabajo.evaluaciones ?? [],
          estado: trabajo.estado ?? EstadoTrabajo.EN_EVALUACION,
          passwordTrabajo: trabajo.passwordTrabajo ?? ''
        }));

        this.cargando = false;

        console.log('Trabajos cargados en pantalla:', this.listadoTrabajos);
        console.log('Cantidad:', this.listadoTrabajos.length);

        this.cd.detectChanges();
      },
      error: err => {
        console.error('Error al recuperar los trabajos técnicos:', err);

        this.mensajeError = 'No se pudieron recuperar las entregas de trabajos en este momento.';
        this.listadoTrabajos = [];
        this.cargando = false;

        this.cd.detectChanges();
      }
    });
  }
}