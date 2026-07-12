import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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
import { TrabajoService } from '../../../service/trabajo.service';

@Component({
  selector: 'app-asignacion-trabajo',
  imports: [InfoPaper],
  templateUrl: './asignacion-trabajo.html',
  styleUrl: './asignacion-trabajo.css',
})
export class AsignacionTrabajo implements OnInit {

  id = {
    organizador: 1,
    evaluador: 0,
    trabajo: 0
  };
  

  trabajo!:Trabajo;
  mensajeError = '';
  

asignaciones: AsignacionEvaluacion[] = [];
usuariosTodos: Usuario[] = [];

  asignados: Usuario[] = [];
  evaluadores: Usuario[] = [];

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private userService: UsuarioService,
    private asignaService: AsignacionService,
    private trabajoService: TrabajoService,
      private cdr: ChangeDetectorRef
  ) {
    
    const usuario = this.authService.getUsuarioActual();

    if (usuario?.id != null) {
      this.id.organizador = usuario.id;
    } else {
      this.mensajeError = 'No se pudo obtener el usuario autenticado.';
    }

   this.id.trabajo = Number(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {

    this.trabajoService.get(this.id.trabajo).subscribe({
    next: (trabajo) => {
      this.trabajo = trabajo;
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error al obtener los trabajos', err);
      // Si tenés una variable para mostrar el error en la vista:
      // this.mensajeError = 'No se pudieron cargar los trabajos.';
    }
  });
    
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
        this.cdr.detectChanges();
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
        asignacion.trabajo.id == this.id.trabajo &&
        asignacion.evaluador.id == usuario.id
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

  asignar(idE?: number): void {
  if (idE == null) {
    console.error('No se recibió un ID válido.');
    return;
  }

  const asignacion: AsignacionEvaluacion = {
    evaluador:{id:idE} as Usuario,
    trabajo: this.trabajo,
    asignadoPor:{id:this.id.organizador} as Usuario,
    estado:EstadoAsignacion.PENDIENTE
  };

  this.asignaService.crear(asignacion).subscribe({
    next: (nuevaAsignacion) => {
      console.log('Asignación creada correctamente.', nuevaAsignacion);

      this.asignaciones.push(nuevaAsignacion);

      //this.inicializarEvaluadores();

       this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error al crear la asignación.', err);
      // Si tenés una variable para mensajes:
      // this.mensajeError = 'No se pudo crear la asignación.';
    }
  });
}

}

