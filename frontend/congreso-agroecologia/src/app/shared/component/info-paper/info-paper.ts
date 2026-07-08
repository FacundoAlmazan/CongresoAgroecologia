import { Component, Input } from '@angular/core';
import { Trabajo } from '../../model/trabajo.model';
import { EstadoTrabajo } from '../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../model/TipoTrabajo.enum.model';

@Component({
  selector: 'app-info-paper',
  imports: [],
  templateUrl: './info-paper.html',
  styleUrl: './info-paper.css',
})
export class InfoPaper {
  @Input() trabajo?:Trabajo=MOCK_TRABAJO;

  
}

const MOCK_TRABAJO:Trabajo={
      id: 3,
      titulo: 'Biorremediación de Suelos Mediante Microorganismos Nativos',
      resumen: 'Evaluación del uso de bacterias del género Pseudomonas aisladas de zonas industriales para la degradación de hidrocarburos en suelos agrícolas contaminados.',
      archivo: 'documento_ag_2026_003.pdf',
      passwordTrabajo: 'microbio456',
      fechaEnvio:'2026 - 08 - 21',
      tipo: TipoTrabajo.PROPUESTA_TALLER,
      estado: EstadoTrabajo.ACEPTADO,     // Mapea a 'Aceptado'
      autores: [
        {
          id: 103,
          nombre: 'Martín',
          apellido: 'Silva',
          organizacion: 'Centro de Biotecnología',
          pais: 'Uruguay',
          email: 'msilva@biotech.org.uy',
          ordenAutor: 1,
          trabajo: {} as Trabajo
        },
        {
          id: 104,
          nombre: 'Laura',
          apellido: 'Gómez',
          organizacion: 'Universidad de la República',
          pais: 'Uruguay',
          email: 'lgomez@udelar.edu.uy',
          ordenAutor: 2,
          trabajo: {} as Trabajo
        }
      ],
      ejesTematicos: [],
      asignaciones: [],
      evaluaciones: [] // 5 comentarios
    }