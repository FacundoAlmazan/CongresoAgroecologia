import { Component, OnInit } from '@angular/core';
import { InfoPaper } from "../info-paper/info-paper";
import { CommonModule } from '@angular/common';
import { Evaluacion } from '../../model/evaluacion.model';
import { Trabajo } from '../../model/trabajo.model';
import { Usuario } from '../../model/usuario.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
import { FormsModule } from '@angular/forms';
import { EvaluacionService } from '../../service/evaluacion.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-formulario-evaluacion',
  imports: [InfoPaper,CommonModule,FormsModule],
  templateUrl: './formulario-evaluacion.html',
  styleUrl: './formulario-evaluacion.css',
})
export class FormularioEvaluacion implements OnInit {

  Recomendacion=Recomendacion;
  evaluacion: Evaluacion = this.inicializarEvaluacion();

  enviando=false;
  trabajo!: Trabajo;

  constructor(
    private evaluacionService: EvaluacionService,
     private location: Location,
  ){}

  
ngOnInit(): void {
  this.trabajo = history.state.trabajo;
}
inicializarEvaluacion():Evaluacion{
  return {

  recomendacion: Recomendacion.APROBADO_CON_CORRECCION,
  comentarios: '',
  pertinenteCongreso: false,
  identificoAutores: false,
  conflictoIntereses: false,
  ejeTematicoCorrecto: false,
  lenguajeAdecuado: false,
  tituloAdecuado: false,
  resumenAdecuado: false,
  palabrasClaveAdecuadas: false,
  bibliografiaApa: false,
  bibliografiaCompleta: false,
  introduccionAdecuada: false,
  objetivosAdecuados: false,
  metodologiaAdecuada: false,
  resultadosAdecuados: false,
  tablasFigurasAdecuadas: false,
  conclusionesAdecuadas: false,
  tipoTrabajoEvaluado: undefined,
  modalidadRecomendada: undefined,
  comentariosAutores: 'Algo muy interesante de parte del autor',
  comentariosComision: '',
  archivoCorrecciones: '',
  trabajo: this.trabajo,
  evaluador: {id:1} as Usuario,
  
  
};
}


enviar(): void {
  this.enviando = true;
  console.log(this.evaluacion)
  this.evaluacion.trabajo=this.trabajo;
  this.evaluacionService.crear(this.evaluacion).subscribe({
    next: (evaluacionCreada) => {
      this.evaluacion = evaluacionCreada;
      this.enviando = false;
    },
    error: (error) => {
      console.error('Error al enviar la evaluación:', error);
      this.enviando = false;
    }
  });
}

cancelar(): void {
  this.location.back();
}

}
