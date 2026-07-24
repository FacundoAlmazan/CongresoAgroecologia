import { ChangeDetectorRef, Component, OnChanges, OnInit } from '@angular/core';
import { CardAsignar } from "./card-asignar/card-asignar";
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { Trabajo } from '../../../model/trabajo.model';
import { TrabajoService } from '../../../service/trabajo.service';

@Component({
  selector: 'app-asignar-evaluadores',
  imports: [CardAsignar],
  templateUrl: './asignar-evaluadores.html',
  styleUrl: './asignar-evaluadores.css',
})
export class AsignarEvaluadores implements OnInit {


trabajos:Trabajo[]=[];

constructor(
    private trabajoService: TrabajoService,
      private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
  this.trabajoService.listar().subscribe({
    next: (trabajos) => {
      this.trabajos = trabajos;
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error al obtener los trabajos', err);

      // this.mensajeError = 'No se pudieron cargar los trabajos.';
    }
  });
}

}





