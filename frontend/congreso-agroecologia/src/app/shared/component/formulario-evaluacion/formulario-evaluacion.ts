import { Component, OnInit } from '@angular/core';
import { InfoPaper } from "../info-paper/info-paper";
import { CommonModule } from '@angular/common';
import { Evaluacion } from '../../model/evaluacion.model';
import { Trabajo } from '../../model/trabajo.model';
import { Usuario } from '../../model/usuario.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formulario-evaluacion',
  imports: [InfoPaper,CommonModule,FormsModule],
  templateUrl: './formulario-evaluacion.html',
  styleUrl: './formulario-evaluacion.css',
})
export class FormularioEvaluacion implements OnInit {

  Recomendacion=Recomendacion;
  evaluacion: Evaluacion = this.inicializarEvaluacion();


trabajo!: Trabajo;

ngOnInit(): void {
  this.trabajo = history.state.trabajo;
}
inicializarEvaluacion():Evaluacion{
  return {
  id: undefined,
  fecha: undefined,
  recomendacion: Recomendacion.RECHAZADO,
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
  comentariosAutores: '',
  comentariosComision: '',
  archivoCorrecciones: '',
  trabajo: {} as Trabajo,
  evaluador: {} as Usuario,
  asignacion: undefined
};
}

}
