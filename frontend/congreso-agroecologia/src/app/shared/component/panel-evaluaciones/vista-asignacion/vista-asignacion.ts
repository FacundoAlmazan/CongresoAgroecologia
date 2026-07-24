import { ChangeDetectorRef, Component } from '@angular/core';
import { Trabajo } from '../../../model/trabajo.model';
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AsignacionEvaluacion } from '../../../model/asignacionEvaluacion.model';
import { TipoRol } from '../../../model/TipoRol.enum.model';
import { Usuario } from '../../../model/usuario.model';
import { EstadoAsignacion } from '../../../model/EstadoAsignacion.enum.model';
import { AsignacionService } from '../../../service/asignacion.service';
import { Router } from '@angular/router';
import { TrabajoService } from '../../../service/trabajo.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vista-asignacion',
  imports: [RouterLink,CommonModule],
  templateUrl: './vista-asignacion.html',
  styleUrl: './vista-asignacion.css',
})
export class VistaAsignacion {

  idEvaluador?:number;
  asignaciones: AsignacionEvaluacion[]=[];
  asignacionesAceptadas: AsignacionEvaluacion[]=[];
  misAsignaciones: AsignacionEvaluacion[]=[];
  asignacionesFinalizadas:AsignacionEvaluacion[]=[];

  btnStyle='';
  asignados=true;
  peticiones=false;
  evaluados=false;
 
 
 
constructor(
  private asignacionService:AsignacionService,
private route: ActivatedRoute,
private trabajoService:TrabajoService,
private router: Router,
private cdr: ChangeDetectorRef
){}


ngOnInit(): void {

  // Simular usuario autenticado
  this.idEvaluador = 1;
  this.cargarAsignaciones();
  
}

dirigir(asignacion:AsignacionEvaluacion){
  this.router.navigate(
  ['/formulario_asignacion', asignacion.id],
  {
    state: {
      asignacion: asignacion
    }
  }
);
}

dirigirFormEvaluacion(trabajo:Trabajo){
  this.router.navigate(
  ['/formulario_evaluacion', trabajo.id],
  {
    state: {
       trabajo: trabajo
    }
  }
);
}
cargarAsignaciones(): void {

  this.asignacionService.listar().subscribe({
    next: (asignaciones) => {
      this.misAsignaciones = asignaciones;
      if(this.idEvaluador){
      this.filtrarAsignaciones(this.idEvaluador);
    }
    this.cdr.detectChanges();
      //this.cargando = false;
    },
    error: (err) => {
      console.error('Error al obtener las asignaciones', err);
      //this.mensajeError = 'No se pudieron cargar las asignaciones.';
      //this.cargando = false;
    }
  });

}


 
 cambiarPestania(nro:number){
  if(nro==1){
    this.asignados=true;
    this.peticiones=false;
    this.evaluados=false;
  }

  if(nro==2){
    this.peticiones=true;
    this.evaluados=false;
    this.asignados=false;
  }

  if(nro==3){
    this.evaluados=true;
    this.asignados=false;
    this.peticiones=false;
  }
  this.cdr.detectChanges();
 }
 
  filtrarAsignaciones(idEvaluador: number): void {
    
    //todas las asignaciones
    this.misAsignaciones=this.misAsignaciones.filter(
    asignacion => (asignacion.evaluador.id === idEvaluador)
  );

  //solo las asignaciones pendientes
  this.asignaciones = this.misAsignaciones.filter(
    asignacion => (asignacion.estado==EstadoAsignacion.PENDIENTE)
  );

  //Solo las asignaciones aceptadas de trabajos que todavia están en evaluacion
  this.asignacionesAceptadas = this.misAsignaciones.filter(
    asignacion => (asignacion.estado==EstadoAsignacion.ACEPTADA)
  );

//Las que ya fueron corregidas
  this.asignacionesFinalizadas= this.asignacionesAceptadas.filter(
    asignacion => (asignacion.finalizado)
  );

this.asignacionesAceptadas = this.asignacionesAceptadas.filter(
    asignacion => (!asignacion.finalizado)
  );
 this.cdr.detectChanges();
}

  



  }
  
  