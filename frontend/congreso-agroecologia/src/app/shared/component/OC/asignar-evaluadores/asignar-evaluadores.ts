import { Component } from '@angular/core';
import { CardAsignar } from "./card-asignar/card-asignar";
import { EstadoTrabajo } from '../../../model/EstadoTrabajo.enum.model';
import { TipoTrabajo } from '../../../model/TipoTrabajo.enum.model';
import { Trabajo } from '../../../model/trabajo.model';

@Component({
  selector: 'app-asignar-evaluadores',
  imports: [CardAsignar],
  templateUrl: './asignar-evaluadores.html',
  styleUrl: './asignar-evaluadores.css',
})
export class AsignarEvaluadores {


trabajos=MOCK_PAPERS;



}







const MOCK_PAPERS: Trabajo[] = [
  {
    id: 1,
    titulo: 'Implementación de Sistemas Agroforestales en la Cuenca del Plata',
    resumen: 'Este estudio analiza la viabilidad técnica y económica de la integración de especies arbóreas nativas con cultivos agrícolas tradicionales en las regiones ribereñas.',
    archivo: 'documento_ag_2026_001.pdf',
    passwordTrabajo: 'securePass123',
    telefonoContacto: '+56912345678',
    tipo: TipoTrabajo.RELATO_EXPERIENCIA, // Asumiendo valores de tus Enums
    estado: EstadoTrabajo.EN_EVALUACION,     // Mapea a 'En revisión'
    autores: [
      {
        id: 101,
        nombre: 'Carlos',
        apellido: 'Mendoza',
        organizacion: 'Universidad Nacional',
        pais: 'Argentina',
        email: 'carlos.mendoza@edu.ar',
        ordenAutor: 1,
        trabajo: {} as Trabajo // Evita referencia circular en el mock
      }
    ],
    ejesTematicos: [],
    asignaciones: [],
    evaluaciones: [] // Arreglo vacío representa 0 comentarios
  },
  {
    id: 2,
    titulo: 'Soberanía Alimentaria: Perspectiva de las Mujeres Rurales en el Biobío',
    resumen: 'Investigación de campo enfocada en el rol de las comunidades de mujeres en la preservación de semillas tradicionales y la gestión de huertos comunitarios.',
    archivo: 'documento_ag_2026_002.pdf',
    passwordTrabajo: 'biobio2026',
    telefonoContacto: '+56987654321',
    tipo: TipoTrabajo.RELATO_EXPERIENCIA,
    estado: EstadoTrabajo.ACEPTADO_CON_CORRECCION,   // Mapea a 'Incompleto'
    autores: [
      {
        id: 102,
        nombre: 'Elena',
        apellido: 'Rojas',
        organizacion: 'Instituto de Agroecología',
        pais: 'Chile',
        email: 'elena.rojas@instituto.cl',
        ordenAutor: 1,
        trabajo: {} as Trabajo
      }
    ],
    ejesTematicos: [],
    asignaciones: [],
    evaluaciones: [] // 3 elementos representan 3 comentarios
  },
  {
    id: 3,
    titulo: 'Biorremediación de Suelos Mediante Microorganismos Nativos',
    resumen: 'Evaluación del uso de bacterias del género Pseudomonas aisladas de zonas industriales para la degradación de hidrocarburos en suelos agrícolas contaminados.',
    archivo: 'documento_ag_2026_003.pdf',
    passwordTrabajo: 'microbio456',
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
  },
  {
    id: 4,
    titulo: 'Estudio Comparativo de Semillas Híbridas en Climas Tropicales',
    resumen: 'Análisis del rendimiento de diferentes variedades de maíz híbrido frente a condiciones de estrés hídrico prolongado en zonas de cultivo de baja altitud.',
    archivo: 'documento_ag_2026_004.pdf',
    passwordTrabajo: 'hybridX99',
    tipo: TipoTrabajo.PROPUESTA_TALLER,
    estado: EstadoTrabajo.RECHAZADO,     // Mapea a 'Rechazado'
    autores: [
      {
        id: 105,
        nombre: 'Jorge',
        apellido: 'Prendes',
        organizacion: 'Corporación Agrícola',
        pais: 'Brasil',
        email: 'j.prendes@agrocrop.br',
        ordenAutor: 1,
        trabajo: {} as Trabajo
      }
    ],
    ejesTematicos: [],
    asignaciones: [],
    evaluaciones: [] // 2 comentarios
  }
];