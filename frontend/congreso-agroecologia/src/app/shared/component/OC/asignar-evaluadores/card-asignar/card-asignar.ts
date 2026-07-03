import { Component } from '@angular/core';
import { Trabajo } from '../../../../model/trabajo.model';
import { TipoTrabajo } from '../../../../model/TipoTrabajo.enum.model';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-card-asignar',
  imports: [RouterLink],
  templateUrl: './card-asignar.html',
  styleUrl: './card-asignar.css',
})
export class CardAsignar {
  max=2;

  trabajo: Trabajo={
    id: 1,
    titulo: 'Nombre de la variable en TS',
    tipo: TipoTrabajo.TRABAJO_CIENTIFICO,
    ejesTematicos: [],
    autores: [],
    fechaEnvio:'No encontrado'
  }

}
