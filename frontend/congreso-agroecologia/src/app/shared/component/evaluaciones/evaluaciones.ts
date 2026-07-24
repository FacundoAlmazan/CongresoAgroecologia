import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router, RouterLink } from "@angular/router";
import { Evaluacion } from '../../model/evaluacion.model';
import { EvaluacionService } from '../../service/evaluacion.service';
import { Injectable } from '@angular/core';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
@Component({
  selector: 'app-evaluaciones',
  imports: [RouterLink,CommonModule],
  templateUrl: './evaluaciones.html',
  styleUrl: './evaluaciones.css',
})
export class Evaluaciones implements OnInit {
  evaluaciones:Evaluacion[]=[];
  idTrabajo?:number;
  Recomendacion=Recomendacion;
  constructor (private evaluacionService: EvaluacionService,
    private cdr: ChangeDetectorRef,
    private router: Router,
      private route: ActivatedRoute,
  ){}


  ngOnInit():void{
    this.idTrabajo = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarEvaluaciones(this.idTrabajo);
  }

  cargarEvaluaciones(idTrabajo?: number): void {
  if (idTrabajo == null) {
    this.evaluaciones = [];
    return;
  }

  this.evaluacionService.listarPorTrabajo(idTrabajo).subscribe({
    next: (evaluaciones: Evaluacion[]) => {
      this.evaluaciones = evaluaciones;
      this.cdr.detectChanges();
    },
    error: (error) => {
      console.error('Error al cargar las evaluaciones:', error);
      this.evaluaciones = [];
    }
  });
}


dirigir(eva:Evaluacion):void{
  this.router.navigate(
  ['/resultado_evaluacion'],
  {
    state: {
      evaluacion:eva
    }
  }
);
}
}
