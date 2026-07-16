import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { concat, finalize, Observable, toArray } from 'rxjs';

import { Usuario } from '../../../model/usuario.model';
import { TipoRol } from '../../../model/TipoRol.enum.model';
import { UsuarioService } from '../../../service/usuario.service';

@Component({
  selector: 'app-control-card',
  imports: [CommonModule],
  templateUrl: './control-card.html',
  styleUrl: './control-card.css',
})
export class ControlCard implements OnInit {
  @Input({ required: true }) user!: Usuario;

  readonly TipoRol = TipoRol;

  mensajeError = '';
  guardando = false;
  cambios = false;

  permisos = {
    esOrganizador: false,
    esAdmin: false,
    esEvaluador: false,
    esActivo: false,
  };

  estilos = {
    activo: 'bg-primary text-on-primary shadow-sm active:scale-95',
    inactivo:
      'border-2 border-outline-variant/50 text-outline-variant hover:bg-surface-container-low',
  };

  constructor(
    private readonly usuarioService: UsuarioService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.sincronizarFormularioConUsuario();
  }

  cambiarPermiso(rol: TipoRol): void {
    this.mensajeError = '';
    this.cambios = true;

    switch (rol) {
      case TipoRol.ADMIN:
        this.permisos.esAdmin = !this.permisos.esAdmin;
        break;
      case TipoRol.EVALUADOR:
        this.permisos.esEvaluador = !this.permisos.esEvaluador;
        break;
      case TipoRol.OC:
        this.permisos.esOrganizador = !this.permisos.esOrganizador;
        break;
    }
  }

  cambiarActivo(): void {
    this.mensajeError = '';
    this.cambios = true;
    this.permisos.esActivo = !this.permisos.esActivo;
  }

  guardar(): void {
    if (this.user.id == null || this.guardando) {
      return;
    }

    const operaciones: Observable<Usuario>[] = [];

    this.agregarOperacionRol(operaciones, TipoRol.ADMIN, this.permisos.esAdmin);
    this.agregarOperacionRol(operaciones, TipoRol.EVALUADOR, this.permisos.esEvaluador);
    this.agregarOperacionRol(operaciones, TipoRol.OC, this.permisos.esOrganizador);

    if (this.permisos.esActivo !== this.user.activo) {
      operaciones.push(
        this.permisos.esActivo
          ? this.usuarioService.activar(this.user.id)
          : this.usuarioService.desactivar(this.user.id)
      );
    }

    if (operaciones.length === 0) {
      this.sincronizarFormularioConUsuario();
      return;
    }

    this.guardando = true;
    this.mensajeError = '';

    concat(...operaciones)
      .pipe(
        toArray(),
        finalize(() => {
          this.guardando = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (respuestas) => {
          const usuarioActualizado = respuestas.at(-1);

          if (usuarioActualizado) {
            this.user = {
              ...usuarioActualizado,
              roles: usuarioActualizado.roles ?? [],
            };
          }

          this.sincronizarFormularioConUsuario();
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error al guardar el usuario', error);
          this.mensajeError = 'No se pudieron guardar todos los cambios del usuario.';
          this.cambios = true;
          this.cdr.detectChanges();
        },
      });
  }

  cancelar(): void {
    this.mensajeError = '';
    this.sincronizarFormularioConUsuario();
  }

  private agregarOperacionRol(
    operaciones: Observable<Usuario>[],
    rol: TipoRol,
    debeTenerlo: boolean
  ): void {
    const tieneRol = (this.user.roles ?? []).some(
      (rolActual) => rolActual.nombre === rol
    );

    if (tieneRol === debeTenerlo || this.user.id == null) {
      return;
    }

    operaciones.push(
      debeTenerlo
        ? this.usuarioService.asignarRol(this.user.id, rol)
        : this.usuarioService.quitarRol(this.user.id, rol)
    );
  }

  private sincronizarFormularioConUsuario(): void {
    const roles = this.user.roles ?? [];

    this.permisos.esAdmin = roles.some((rol) => rol.nombre === TipoRol.ADMIN);
    this.permisos.esEvaluador = roles.some((rol) => rol.nombre === TipoRol.EVALUADOR);
    this.permisos.esOrganizador = roles.some((rol) => rol.nombre === TipoRol.OC);
    this.permisos.esActivo = this.user.activo;

    this.cambios = false;
  }
}