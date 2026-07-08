import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { Trabajo } from '../../model/trabajo.model';
import { TipoTrabajo } from '../../model/TipoTrabajo.enum.model';
import { AutorTrabajo } from '../../model/autorTrabajo.model';
import { TrabajoService } from '../../service/trabajo.service';

@Component({
  selector: 'app-formulario-paper',
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-paper.html',
  styleUrl: './formulario-paper.css',
})
export class FormularioPaper implements OnInit {
  TipoTrabajo = TipoTrabajo;

  private esEdicion = false;

  paper: Trabajo = {
    titulo: '',
    resumen: '',
    passwordTrabajo: '',
    tipo: TipoTrabajo.RELATO_EXPERIENCIA,
    ejesTematicos: [],
    autores: [],
  };
  mensajeError = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private trabajoService: TrabajoService
  ) {}

  ngOnInit(): void {
  const idParam = this.route.snapshot.paramMap.get('id');

  if (idParam) {
    this.esEdicion = true;
    const id = Number(idParam);

    this.trabajoService.get(id).subscribe({
      next: data => {
        console.log('Trabajo cargado para edición:', data);

        this.paper = {
          ...data,
          titulo: data.titulo ?? '',
          resumen: data.resumen ?? '',
          passwordTrabajo: data.passwordTrabajo ?? '',
          tipo: data.tipo ?? TipoTrabajo.RELATO_EXPERIENCIA,
          ejesTematicos: data.ejesTematicos ?? [],
          autores: data.autores ?? [],
        };
      },
      error: err => {
        console.error('Error al cargar el trabajo para edición:', err);
        this.mensajeError = 'No se pudo cargar el trabajo para editar.';
        this.inicializarObjetoVacio();
      }
    });
  } else {
    this.esEdicion = false;
    this.inicializarObjetoVacio();
  }
}
  private inicializarObjetoVacio(): void {
    this.paper = {
      titulo: '',
      resumen: '',
      passwordTrabajo: '',
      tipo: TipoTrabajo.RELATO_EXPERIENCIA,
      ejesTematicos: [],
      autores: [],
    };
  }

  enviarFormulario(): void {
    this.mensajeError = '';

    if (!this.paper) {
      this.mensajeError = 'El formulario todavía no está listo.';
      return;
    }

    if (!this.paper.titulo || this.paper.titulo.trim() === '') {
      this.mensajeError = 'El título del trabajo es obligatorio.';
      return;
    }

    if (!this.paper.passwordTrabajo || this.paper.passwordTrabajo.trim() === '') {
      this.mensajeError = 'La contraseña del trabajo es obligatoria.';
      return;
    }

    console.log('Trabajo enviado:', this.paper);
    console.log('Trabajo enviado JSON:', JSON.stringify(this.paper));

    if (this.esEdicion && this.paper.id) {
      this.modificar();
    } else {
      this.crear();
    }
  }

  private crear(): void {
    this.trabajoService.crear(this.paper).subscribe({
      next: trabajoCreado => {
        console.log('Trabajo creado con éxito:', trabajoCreado);
        this.router.navigate(['/panel_autor']).then(() => window.scrollTo(0, 0));
      },
      error: err => {
        console.error('Error en el servicio al crear:', err);
        this.mensajeError = err?.error?.message ?? 'Ocurrió un error al intentar registrar el trabajo técnico.';
      }
    });
  }

  private modificar(): void {
    const id = this.paper.id!;

    this.trabajoService.modificar(id, this.paper).subscribe({
      next: trabajoModificado => {
        console.log('Trabajo actualizado con éxito:', trabajoModificado);
        this.router.navigate(['/panel_autor']).then(() => window.scrollTo(0, 0));
      },
      error: err => {
        console.error('Error en el servicio al modificar:', err);
        this.mensajeError = err?.error?.message ?? 'Ocurrió un error al intentar guardar los cambios del trabajo.';
      }
    });
  }

  validarAutor(autor: AutorTrabajo): boolean {
    if (!autor) return false;

    const tieneNombre = !!autor.nombre?.trim();
    const tieneApellido = !!autor.apellido?.trim();
    const tieneEmail = !!autor.email?.trim();
    const emailValido = tieneEmail && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(autor.email);

    return tieneNombre && tieneApellido && emailValido;
  }
}