import { ChangeDetectorRef, Component } from '@angular/core';
import { Usuario } from '../../model/usuario.model';
import { TipoRol } from '../../model/TipoRol.enum.model';
import { ControlCard } from "./control-card/control-card";
import { TipoIdentificacion } from '../../model/TipoIdentificacion.enum.model';
import { UsuarioService } from '../../service/usuario.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-panel-control',
  imports: [ControlCard, CommonModule, RouterLink],
  templateUrl: './panel-control.html',
  styleUrl: './panel-control.css',
})
export class PanelControl {
 usuarios:Usuario[]=[];
//usuariosMock: Usuario[]=USUARIOS_MOCK;

cargando=false;
mensajeError='';


  constructor(private usuarioService: UsuarioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnChanges(){this.cargarUsuarios();}
  ngOnInit(): void {
    
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.cargando = true;
    this.mensajeError = '';

    this.usuarioService.listar().subscribe({
      next: (usuariose) => {
        this.usuarios = usuariose;
        console.log(this.usuarios);
          this.cdr.detectChanges();

        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener los usuarios', err);
        this.mensajeError = 'No se pudieron cargar los usuarios.';
        this.cargando = false;
      }
    });
  }



}


export const USUARIOS_MOCK: Usuario[] = [
  {
    id: 1,
    nombre: 'Juan',
    apellido: 'Pérez',
    email: 'juan.perez@universidad.edu',
    telefono: '1123456789',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '30123456',
    nacionalidad: 'Argentina',
    institucion: 'Universidad Nacional',
    activo: true,
    roles: [
      { id: 1, nombre: TipoRol.ADMIN }
    ]
  },
  {
    id: 2,
    nombre: 'María',
    apellido: 'González',
    email: 'maria.gonzalez@universidad.edu',
    telefono: '1134567890',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '28987654',
    nacionalidad: 'Argentina',
    institucion: 'Universidad Nacional',
    activo: true,
    roles: [
      { id: 2, nombre: TipoRol.OC }
    ]
  },
  {
    id: 3,
    nombre: 'Carlos',
    apellido: 'Rodríguez',
    email: 'carlos.rodriguez@universidad.edu',
    telefono: '1145678901',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '33456789',
    nacionalidad: 'Argentina',
    institucion: 'Universidad Tecnológica',
    activo: true,
    roles: [
      { id: 3, nombre: TipoRol.EVALUADOR }
    ]
  },
  {
    id: 4,
    nombre: 'Laura',
    apellido: 'Fernández',
    email: 'laura.fernandez@universidad.edu',
    telefono: '1156789012',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '27543210',
    nacionalidad: 'Argentina',
    institucion: 'Universidad Nacional del Sur',
    activo: false,
    roles: [
      { id: 2, nombre: TipoRol.OC },
      { id: 3, nombre: TipoRol.EVALUADOR }
    ]
  },
  {
    id: 5,
    nombre: 'Diego',
    apellido: 'Martínez',
    email: 'diego.martinez@universidad.edu',
    telefono: '1167890123',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '35987654',
    nacionalidad: 'Uruguaya',
    institucion: 'Universidad de la República',
    activo: true,
    roles: [
      { id: 3, nombre: TipoRol.EVALUADOR }
    ]
  },
  {
    id: 6,
    nombre: 'Ana',
    apellido: 'López',
    email: 'ana.lopez@universidad.edu',
    telefono: '1178901234',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '32123456',
    nacionalidad: 'Argentina',
    institucion: 'Universidad Nacional de Córdoba',
    activo: true,
    roles: [
      { id: 1, nombre: TipoRol.ADMIN },
      { id: 2, nombre: TipoRol.OC }
    ]
  }
];
