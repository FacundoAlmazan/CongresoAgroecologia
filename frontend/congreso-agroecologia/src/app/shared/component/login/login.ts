import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  credenciales = { numeroIdentificacion: '', password: '' };
  error = '';
  cargando = false;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.error = '';
    this.cargando = true;

    this.authService.login(
      this.credenciales.numeroIdentificacion,
      this.credenciales.password
    ).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/home']);
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.error = err?.error?.message ?? 'DNI o contraseña incorrectos.';
      }
    });
  }
}
