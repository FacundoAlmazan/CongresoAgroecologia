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

  btnStyle='';
  asignados=true;
 
 
 
 
 cambiarPestania(){
  this.asignados=!this.asignados;

 }
 
  filtrarAsignaciones(idEvaluador: number): void {
    
  this.asignaciones = this.misAsignaciones.filter(
    asignacion => (asignacion.evaluador.id === idEvaluador && asignacion.estado==EstadoAsignacion.PENDIENTE)
  );
}

  filtrarAceptadas(idEvaluador: number): void {
    
  this.asignacionesAceptadas = this.misAsignaciones.filter(
    asignacion => (asignacion.evaluador.id === idEvaluador && asignacion.estado==EstadoAsignacion.ACEPTADA)
  );
}
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


cargarAsignaciones(): void {

  this.asignacionService.listar().subscribe({
    next: (asignaciones) => {
      this.misAsignaciones = asignaciones;
      if(this.idEvaluador){
      this.filtrarAsignaciones(this.idEvaluador);
    this.filtrarAceptadas(this.idEvaluador);}
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



  }
  
  