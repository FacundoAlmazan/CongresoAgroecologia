import { Component } from '@angular/core';
import { InfoPaper } from "../../info-paper/info-paper";
import { RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-formulario-asignacion',
  imports: [InfoPaper, RouterLink, CommonModule],
  templateUrl: './formulario-asignacion.html',
  styleUrl: './formulario-asignacion.css',
})
export class FormularioAsignacion {
  
  seleccion=false;
  aceptado=false;


  enviar(){

    //Acá se hace las consultas http
  }
}
