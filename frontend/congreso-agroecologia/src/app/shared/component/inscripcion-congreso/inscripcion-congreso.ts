import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PagoService } from '../../service/pago.service';
import { AuthService } from '../../service/auth.service';
import { Inscripcion } from '../../model/inscripcion.model';

import { EstadoPago } from '../../model/EstadoPago.enum.model';

type CategoriaInscripcion = {
  nombre: string;
  monto: number;
};

@Component({
  selector: 'app-inscripcion-congreso',
  imports: [FormsModule],
  templateUrl: './inscripcion-congreso.html',
  styleUrl: './inscripcion-congreso.css'
})
export class InscripcionCongreso {
  instancias = ['Pre-congreso', 'Congreso', 'Post-congreso'];

  categorias: CategoriaInscripcion[] = [
    { nombre: 'Estudiante de grado', monto: 35000 },
    { nombre: 'Socio SAAE', monto: 75000 },
    { nombre: 'Productor', monto: 50000 },
    { nombre: 'No socio', monto: 150000 }
  ];

  instanciaSeleccionada = 'Congreso';
  categoriaSeleccionada: CategoriaInscripcion = this.categorias[0];

  comprobante = '';
  comprobanteAcreditacion = '';

  requiereFactura = false;
  razonSocial = '';
  cuit = '';
  condicionIva = '';
  domicilioFiscal = '';
  emailFactura = '';

  requiereAdecuacion = false;
  detalleAdecuacion = '';

  tieneAlergiaOIntolerancia = false;
  detalleAlergiaOIntolerancia = '';

  observaciones = '';

  cargando = false;
  error = '';
  exito = '';

  constructor(
    private pagoService: PagoService,
    private authService: AuthService,
    private router: Router
  ) {}

  seleccionarInstancia(instancia: string): void {
    this.instanciaSeleccionada = instancia;
  }

  seleccionarCategoria(categoria: CategoriaInscripcion): void {
    this.categoriaSeleccionada = categoria;
  }

  seleccionarComprobantePago(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.comprobante = input.files?.[0]?.name ?? '';
  }

  seleccionarComprobanteAcreditacion(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.comprobanteAcreditacion = input.files?.[0]?.name ?? '';
  }

  finalizarInscripcion(): void {
    this.error = '';
    this.exito = '';

    const usuarioActual = this.authService.getUsuarioActual();

    if (!usuarioActual?.id) {
      this.error = 'Tenés que iniciar sesión antes de inscribirte.';
      this.router.navigate(['/login']);
      return;
    }

    if (!this.comprobante) {
      this.error = 'Debés cargar el comprobante de pago.';
      return;
    }

    const inscripcion: Inscripcion = {
      usuario: { id: usuarioActual.id },
      monto: this.categoriaSeleccionada.monto,
      fechaPago: this.fechaHoy(),
      comprobante: this.comprobante,
      comprobanteAcreditacion: this.comprobanteAcreditacion,
      tipoInscripcion: this.categoriaSeleccionada.nombre,
      filiacionInstitucional: usuarioActual.institucion ?? '',
      formasParticipacion: this.instanciaSeleccionada,
      requiereFactura: this.requiereFactura,
      requiereAdecuacion: this.requiereAdecuacion,
      detalleAdecuacion: this.detalleAdecuacion,
      tieneAlergiaOIntolerancia: this.tieneAlergiaOIntolerancia,
      detalleAlergiaOIntolerancia: this.detalleAlergiaOIntolerancia,
      observaciones: this.observaciones,
      efectivo: false,
      estado: EstadoPago.PENDIENTE
    };

    if (this.requiereFactura) {
      inscripcion.datosFactura = {
        razonSocial: this.razonSocial,
        cuit: this.cuit,
        condicionIva: this.condicionIva,
        domicilioFiscal: this.domicilioFiscal,
        emailFactura: this.emailFactura
      };
    }

    console.log('Inscripción enviada:', inscripcion);

    this.cargando = true;

    this.pagoService.registrar(inscripcion).subscribe({
      next: creada => {
        console.log('Inscripción creada:', creada);
        this.cargando = false;
        localStorage.setItem('inscripcionActual', JSON.stringify(creada));
        this.router.navigate(['/home']).then(() => {
          window.scrollTo(0, 0);
        }); 
      },
      error: err => {
        console.error('Error registrando inscripción:', err);
        this.cargando = false;
        this.error = err?.error?.message ?? 'No se pudo registrar la inscripción.';
      }
    });
    
  }

  private fechaHoy(): string {
    return new Date().toISOString().substring(0, 10);
  }
}
