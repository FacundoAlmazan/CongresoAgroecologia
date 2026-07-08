import { Component, OnInit } from '@angular/core';
import { InfoPaper } from "../../info-paper/info-paper";
import { AuthService } from '../../../service/auth.service';
import { ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../../../service/usuario.service';
import { AsignacionService } from '../../../service/asignacion.service';
import { Usuario } from '../../../model/usuario.model';
import { AsignacionEvaluacion } from '../../../model/asignacionEvaluacion.model';
import { TipoRol } from '../../../model/TipoRol.enum.model';
import { EstadoAsignacion } from '../../../model/EstadoAsignacion.enum.model';
import { Trabajo } from '../../../model/trabajo.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';

@Component({
  selector: 'app-asignacion-trabajo',
  imports: [InfoPaper],
  templateUrl: './asignacion-trabajo.html',
  styleUrl: './asignacion-trabajo.css',
})
export class AsignacionTrabajo implements OnInit {

  id = {
    organizador: 0,
    evaluador: 0,
    trabajo: 1
  };
  

  
  mensajeError = '';
  usarMock = true;
  asignaciones: AsignacionEvaluacion[] = [];
  usuariosTodos: Usuario[] = [];
  asignados: Usuario[] = [];
  evaluadores: Usuario[] = [];

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private userService: UsuarioService,
    private asignaService: AsignacionService,
  ) {
    
    const usuario = this.authService.getUsuarioActual();

    if (usuario?.id != null) {
      this.id.organizador = usuario.id;
    } else {
      this.mensajeError = 'No se pudo obtener el usuario autenticado.';
    }

   // this.id.trabajo = Number(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {

  
    if (this.usarMock) {
    this.usuariosTodos = USUARIOS_MOCK;
    this.asignaciones = ASIGNACIONES_MOCK;
        
    this.inicializarAsignados();
    this.inicializarEvaluadores();
    return;
}
    this.inicializarUsuarios();
  }

  inicializarUsuarios(): void {

    this.userService.listar().subscribe({
      next: usuarios => {
        this.usuariosTodos = usuarios;
        this.inicializarAsignaciones();
      },
      error: err => {
        console.error(err);
        this.mensajeError = 'No se pudieron obtener los usuarios.';
      }
    });

  }

  inicializarAsignaciones(): void {

    this.asignaService.listar().subscribe({
      next: asignaciones => {
        this.asignaciones = asignaciones;

        this.inicializarAsignados();
        this.inicializarEvaluadores();
      },
      error: err => {
        console.error(err);
        this.mensajeError = 'No se pudieron obtener las asignaciones.';
      }
    });

  }

  inicializarAsignados(): void {

    this.asignados = this.usuariosTodos.filter(usuario =>
      this.asignaciones.some(asignacion =>
        asignacion.trabajo.id === this.id.trabajo &&
        asignacion.evaluador.id === usuario.id
      )
    );

  }

  inicializarEvaluadores(): void {

    this.evaluadores = this.usuariosTodos.filter(usuario => {

      const esEvaluador =
        usuario.roles.some(r => r.nombre === TipoRol.EVALUADOR);

      const yaAsignado =
        this.asignaciones.some(asignacion =>
          asignacion.trabajo.id === this.id.trabajo &&
          asignacion.evaluador.id === usuario.id
        );

      return esEvaluador && !yaAsignado;

    });

  }

}


export const USUARIOS_MOCK: Usuario[] = [
  {
    id: 1,
    nombre: 'Ana',
    apellido: 'Martínez',
    email: 'ana.martinez@congreso.org',
    numeroIdentificacion: '30111222',
    nacionalidad: 'Argentina',
    institucion: 'UNLP',
    activo: true,
    roles: [{ nombre: TipoRol.EVALUADOR }]
  },
  {
    id: 2,
    nombre: 'Bruno',
    apellido: 'López',
    email: 'bruno.lopez@congreso.org',
    numeroIdentificacion: '28999888',
    nacionalidad: 'Argentina',
    institucion: 'UBA',
    activo: true,
    roles: [{ nombre: TipoRol.EVALUADOR }]
  },
  {
    id: 3,
    nombre: 'Carla',
    apellido: 'Fernández',
    email: 'carla.fernandez@congreso.org',
    numeroIdentificacion: '33444555',
    nacionalidad: 'Argentina',
    institucion: 'UNMdP',
    activo: true,
    roles: [{ nombre: TipoRol.EVALUADOR }]
  },
  {
    id: 4,
    nombre: 'Diego',
    apellido: 'Romero',
    email: 'diego.romero@congreso.org',
    numeroIdentificacion: '27888999',
    nacionalidad: 'Argentina',
    institucion: 'UNS',
    activo: true,
    roles: [{ nombre: TipoRol.EVALUADOR }]
  },
  {
    id: 5,
    nombre: 'Elena',
    apellido: 'Suárez',
    email: 'elena.suarez@congreso.org',
    numeroIdentificacion: '32123456',
    nacionalidad: 'Uruguay',
    institucion: 'UDELAR',
    activo: true,
    roles: [{ nombre: TipoRol.EVALUADOR }]
  },
  {
    id: 6,
    nombre: 'Federico',
    apellido: 'Gómez',
    email: 'federico.gomez@congreso.org',
    numeroIdentificacion: '25666777',
    nacionalidad: 'Argentina',
    institucion: 'UNLP',
    activo: true,
    roles: [{ nombre: TipoRol.OC }]
  }
];

export const ASIGNACIONES_MOCK: AsignacionEvaluacion[] = [
  {
    id: 1,
    trabajo: {
      id: 1
    } as Trabajo,
    evaluador: USUARIOS_MOCK[0], // Ana
    asignadoPor: {
      id: 100
    } as Usuario,
    fechaAsignacion: '2026-07-07',
    estado: EstadoAsignacion.PENDIENTE
  },
  {
    id: 2,
    trabajo: {
      id: 1
    } as Trabajo,
    evaluador: USUARIOS_MOCK[2], // Carla
    asignadoPor: {
      id: 100
    } as Usuario,
    fechaAsignacion: '2026-07-07',
    estado: EstadoAsignacion.ACEPTADA
  },
  {
    id: 3,
    trabajo: {
      id: 1
    } as Trabajo,
    evaluador: USUARIOS_MOCK[4], // Elena
    asignadoPor: {
      id: 100
    } as Usuario,
    fechaAsignacion: '2026-07-07',
    estado: EstadoAsignacion.RECHAZADA
  }
];