import { Component, OnInit } from '@angular/core';
import { InfoPaper } from "../info-paper/info-paper";
import { CommonModule } from '@angular/common';
import { Evaluacion } from '../../model/evaluacion.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';

@Component({
  selector: 'app-resultado-evaluacion',
  imports: [CommonModule],
  templateUrl: './resultado-evaluacion.html',
  styleUrl: './resultado-evaluacion.css',
})
export class ResultadoEvaluacion implements OnInit{
  evaluacion!:Evaluacion;
  Recomendacion=Recomendacion;

  ngOnInit(): void {
  this.evaluacion = history.state.evaluacion;
}
}
