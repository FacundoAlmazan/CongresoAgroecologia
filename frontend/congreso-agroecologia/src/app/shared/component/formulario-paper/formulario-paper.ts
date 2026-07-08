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

  visible = 'hidden';
  visibleCard = 'hidden';

  private esEdicion = false;

  paper: Trabajo = this.crearTrabajoVacio();
  autor: AutorTrabajo = this.inicializarAutor();

  ejeTematicoSeleccionado = '';
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

          this.autor = this.inicializarAutor();
        },
        error: err => {
          console.error('Error al cargar el trabajo', err);
          this.mensajeError = 'No se pudo cargar el trabajo para editar.';
          this.inicializarObjetoVacio();
        }
      });
    } else {
      this.esEdicion = false;
      this.inicializarObjetoVacio();
    }
  }

  private crearTrabajoVacio(): Trabajo {
    return {
      titulo: '',
      resumen: '',
      passwordTrabajo: '',
      tipo: TipoTrabajo.RELATO_EXPERIENCIA,
      ejesTematicos: [],
      autores: [],
    };
  }

  private inicializarObjetoVacio(): void {
    this.paper = this.crearTrabajoVacio();
    this.autor = this.inicializarAutor();
  }

  inicializarAutor(): AutorTrabajo {
    return {
      nombre: '',
      apellido: '',
      organizacion: '',
      pais: '',
      email: '',
      ordenAutor: 0,
      trabajo: {} as Trabajo
    };
  }

  abrirAutor(): void {
    this.autor = this.inicializarAutor();
    this.visible = '';
  }

  cerrarAutor(): void {
    this.visible = 'hidden';
  }

  crearAutor(): void {
    this.mensajeError = '';

    if (!this.validarAutor(this.autor)) {
      this.mensajeError = 'Completá correctamente nombre, apellido y email del autor.';
      return;
    }

    const nuevoAutor: AutorTrabajo = {
      ...this.autor,
      ordenAutor: this.paper.autores.length + 1,
      trabajo: {} as Trabajo
    };

    this.paper.autores.push(nuevoAutor);
    this.autor = this.inicializarAutor();
    this.cerrarAutor();
  }

  eliminarAutor(index: number): void {
    this.paper.autores.splice(index, 1);
    this.paper.autores.forEach((autor, i) => {
      autor.ordenAutor = i + 1;
    });
  }

  abrirCard(autor: AutorTrabajo): void {
    this.autor = autor;
    this.visibleCard = '';
  }

  cerrarCard(): void {
    this.visibleCard = 'hidden';
    this.autor = this.inicializarAutor();
  }

  seleccionarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    const archivo = input.files?.[0];

    this.paper.archivo = archivo?.name ?? '';
  }

  borrarArchivo(): void {
    this.paper.archivo = '';
  }

  validarAutor(autor: AutorTrabajo): boolean {
    if (!autor) return false;

    const tieneNombre = !!autor.nombre?.trim();
    const tieneApellido = !!autor.apellido?.trim();
    const tieneEmail = !!autor.email?.trim();
    const emailValido = tieneEmail && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(autor.email);

    return tieneNombre && tieneApellido && emailValido;
  }

  enviarFormulario(): void {
    this.mensajeError = '';

    if (!this.paper.titulo?.trim()) {
      this.mensajeError = 'El título del trabajo es obligatorio.';
      return;
    }

    if (!this.paper.passwordTrabajo?.trim()) {
      this.mensajeError = 'La contraseña del trabajo es obligatoria.';
      return;
    }

    const trabajo = this.prepararTrabajoParaEnviar();

    console.log('Trabajo enviado:', trabajo);
    console.log('Trabajo enviado JSON:', JSON.stringify(trabajo));

    if (this.esEdicion && trabajo.id) {
      this.modificar(trabajo);
    } else {
      this.crear(trabajo);
    }
  }

  private prepararTrabajoParaEnviar(): Trabajo {
    return {
      ...this.paper,
      ejesTematicos: this.paper.ejesTematicos ?? [],
      autores: (this.paper.autores ?? []).map((autor, index) => ({
        id: autor.id,
        nombre: autor.nombre,
        apellido: autor.apellido,
        organizacion: autor.organizacion,
        pais: autor.pais,
        email: autor.email,
        ordenAutor: autor.ordenAutor || index + 1,
        trabajo: {} as Trabajo
      }))
    };
  }

  private crear(trabajo: Trabajo): void {
    this.trabajoService.crear(trabajo).subscribe({
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

  private modificar(trabajo: Trabajo): void {
    const id = trabajo.id!;

    this.trabajoService.modificar(id, trabajo).subscribe({
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
}
