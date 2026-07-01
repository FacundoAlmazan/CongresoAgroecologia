import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Usuario } from '../../model/usuario.model';
import { UsuarioService } from '../../service/usuario.service';
import { TipoIdentificacion } from '../../model/TipoIdentificacion.enum.model';

@Component({
  selector: 'app-registro',
  imports: [FormsModule, RouterLink],
  templateUrl: './registro.html',
  styleUrl: './registro.css'
})
export class Registro {
  usuario: Usuario = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    telefono: '',
    tipoIdentificacion: TipoIdentificacion.DNI,
    numeroIdentificacion: '',
    nacionalidad: 'Argentina',
    institucion: '',
    activo: true,
    roles: []
  };

  repetirPassword = '';
  error = '';
  exito = '';
  cargando = false;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  registrar(): void {
    this.error = '';
    this.exito = '';

    if (this.usuario.password !== this.repetirPassword) {
      this.error = 'Las contraseñas no coinciden.';
      return;
    }

    this.cargando = true;

    this.usuarioService.crear(this.usuario).subscribe({
      next: () => {
        this.cargando = false;
        this.exito = 'Usuario creado correctamente.';
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.error = err?.error?.message ?? 'No se pudo crear el usuario.';
      }
    });
  }
}