import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { InfoPaper } from "../../info-paper/info-paper";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';
import { AsignacionEvaluacion } from '../../../model/asignacionEvaluacion.model';
import { EstadoAsignacion } from '../../../model/EstadoAsignacion.enum.model';
import { AsignacionService } from '../../../service/asignacion.service';
import { Trabajo } from '../../../model/trabajo.model';
import { TrabajoService } from '../../../service/trabajo.service';


@Component({
  selector: 'app-formulario-asignacion',
  imports: [InfoPaper, RouterLink, CommonModule],
  templateUrl: './formulario-asignacion.html',
  styleUrl: './formulario-asignacion.css',
})
export class FormularioAsignacion implements OnInit {
  
  
  asignacion:AsignacionEvaluacion=history.state.asignacion;
  seleccion=false;
  aceptado=false;
  mensaje='';
  trabajo!: Trabajo;


  constructor(
    private asignacionService:AsignacionService,
     private router: Router,
      private trabajoService:TrabajoService,
    private cdr:ChangeDetectorRef
  ){}
    

    ngOnInit(): void {
    //this.inicializarTrabajo()
    this.asignacion = history.state.asignacion;
  }

inicializarTrabajo(): void {
if(this.asignacion.trabajo.id)
  this.trabajoService.get(this.asignacion.trabajo.id).subscribe({
    next: (trabajo) => {
      this.trabajo = trabajo;
    },
    error: (err) => {
      console.error('Error al obtener el trabajo', err);
      //this.mensajeError = 'No se pudo cargar el trabajo.';
    }
  });

}
  enviar(): void {

  if (!this.seleccion) {
    this.mensaje = 'Debe seleccionar una opción.';
    this.cdr.detectChanges();
    return;
  }

  const peticion = this.aceptado
    ? this.asignacionService.aceptar(this.asignacion.id!)
    : this.asignacionService.rechazar(this.asignacion.id!);

  peticion.subscribe({
    next: (asignacion:AsignacionEvaluacion) => {

      this.asignacion = asignacion;

      
      this.mensaje = this.aceptado
        ? 'La asignación fue aceptada correctamente.'
        : 'La asignación fue rechazada correctamente.';
      this.cdr.detectChanges();
      setTimeout(() => {
        this.router.navigate(['/trabajos_asignados']); 
      }, 1500);

    },
    error: (err) => {

      console.error('Error al responder la asignación', err);

      
      this.mensaje = 'No se pudo registrar la respuesta. Intente nuevamente.';
      this.cdr.detectChanges();

    }
  });

}
}
