import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
export class InscripcionCongreso implements OnInit {
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
  comprobanteContenido = '';
  comprobanteAcreditacion = '';
  comprobanteAcreditacionContenido = '';
  efectivo = false;
  inscripcionExistente: Inscripcion | null = null;

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
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.pagoService.obtenerMia().subscribe({
      next: inscripcion => {
        this.inscripcionExistente = inscripcion;
        if (inscripcion && (
          inscripcion.estado === EstadoPago.OBSERVADO ||
          inscripcion.estado === EstadoPago.RECHAZADO
        )) {
          this.cargarDatosExistentes(inscripcion);
        }
        this.cdr.markForCheck();
      },
      error: () => {
        this.inscripcionExistente = null;
        this.cdr.markForCheck();
      }
    });
  }

  seleccionarInstancia(instancia: string): void {
    this.instanciaSeleccionada = instancia;
  }

  seleccionarCategoria(categoria: CategoriaInscripcion): void {
    this.categoriaSeleccionada = categoria;
  }

  async seleccionarComprobantePago(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    const archivo = input.files?.[0];
    if (!archivo) return;
    this.error = '';
    try {
      this.comprobanteContenido = await this.leerArchivo(archivo);
      this.comprobante = archivo.name;
    } catch (error) {
      this.error = error instanceof Error ? error.message : 'No se pudo leer el archivo.';
    } finally {
      this.cdr.markForCheck();
    }
  }

  async seleccionarComprobanteAcreditacion(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    const archivo = input.files?.[0];
    if (!archivo) return;
    this.error = '';
    try {
      this.comprobanteAcreditacionContenido = await this.leerArchivo(archivo);
      this.comprobanteAcreditacion = archivo.name;
    } catch (error) {
      this.error = error instanceof Error ? error.message : 'No se pudo leer el archivo.';
    } finally {
      this.cdr.markForCheck();
    }
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

    if (!this.efectivo && !this.comprobanteContenido) {
      this.error = 'Debés cargar el comprobante de pago.';
      return;
    }

    const inscripcion: Inscripcion = {
      usuario: { id: usuarioActual.id },
      monto: this.categoriaSeleccionada.monto,
      montoAbonado: this.efectivo ? 0 : this.categoriaSeleccionada.monto,
      fechaPago: this.fechaHoy(),
      comprobante: this.comprobante,
      comprobanteContenido: this.comprobanteContenido,
      comprobanteAcreditacion: this.comprobanteAcreditacion,
      comprobanteAcreditacionContenido: this.comprobanteAcreditacionContenido,
      tipoInscripcion: this.categoriaSeleccionada.nombre,
      filiacionInstitucional: usuarioActual.institucion ?? '',
      formasParticipacion: this.instanciaSeleccionada,
      requiereFactura: this.requiereFactura,
      requiereAdecuacion: this.requiereAdecuacion,
      detalleAdecuacion: this.detalleAdecuacion,
      tieneAlergiaOIntolerancia: this.tieneAlergiaOIntolerancia,
      detalleAlergiaOIntolerancia: this.detalleAlergiaOIntolerancia,
      observaciones: this.observaciones,
      efectivo: this.efectivo,
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

    const esReenvio = Boolean(this.inscripcionExistente && (
      this.inscripcionExistente.estado === EstadoPago.OBSERVADO ||
      this.inscripcionExistente.estado === EstadoPago.RECHAZADO
    ));

    const solicitud = esReenvio
      ? this.pagoService.reenviar(inscripcion)
      : this.pagoService.registrar(inscripcion);

    solicitud.subscribe({
      next: creada => {
        console.log('Inscripción creada:', creada);
        this.cargando = false;
        this.cdr.markForCheck();
        const mensaje = esReenvio
          ? 'Tu inscripción fue reenviada correctamente y quedó pendiente de una nueva revisión.'
          : 'Tu inscripción se registró correctamente y quedó pendiente de revisión.';
        this.router.navigate(['/home'], {
          state: { mensajeInscripcion: mensaje }
        }).then(() => {
          window.scrollTo(0, 0);
        });
      },
      error: err => {
        console.error('Error registrando inscripción:', err);
        this.cargando = false;
        this.error = err?.error?.error ?? err?.error?.message ?? 'No se pudo registrar la inscripción.';
        this.cdr.markForCheck();
      }
    });
    
  }

  private fechaHoy(): string {
    return new Date().toISOString().substring(0, 10);
  }

  private cargarDatosExistentes(inscripcion: Inscripcion): void {
    const categoria = this.categorias.find(c => c.nombre === inscripcion.tipoInscripcion);
    if (categoria) this.categoriaSeleccionada = categoria;
    this.instanciaSeleccionada = inscripcion.formasParticipacion ?? 'Congreso';
    this.comprobante = inscripcion.comprobante ?? '';
    this.comprobanteContenido = inscripcion.comprobanteContenido ?? '';
    this.comprobanteAcreditacion = inscripcion.comprobanteAcreditacion ?? '';
    this.comprobanteAcreditacionContenido = inscripcion.comprobanteAcreditacionContenido ?? '';
    this.efectivo = inscripcion.efectivo;
    this.requiereFactura = inscripcion.requiereFactura;
    this.razonSocial = inscripcion.datosFactura?.razonSocial ?? '';
    this.cuit = inscripcion.datosFactura?.cuit ?? '';
    this.condicionIva = inscripcion.datosFactura?.condicionIva ?? '';
    this.domicilioFiscal = inscripcion.datosFactura?.domicilioFiscal ?? '';
    this.emailFactura = inscripcion.datosFactura?.emailFactura ?? '';
    this.requiereAdecuacion = inscripcion.requiereAdecuacion;
    this.detalleAdecuacion = inscripcion.detalleAdecuacion ?? '';
    this.tieneAlergiaOIntolerancia = inscripcion.tieneAlergiaOIntolerancia;
    this.detalleAlergiaOIntolerancia = inscripcion.detalleAlergiaOIntolerancia ?? '';
    this.observaciones = inscripcion.observaciones ?? '';
  }

  private leerArchivo(archivo: File): Promise<string> {
    if (archivo.size > 5 * 1024 * 1024) {
      this.error = 'El archivo no puede superar los 5 MB.';
      return Promise.reject(new Error(this.error));
    }
    return new Promise((resolve, reject) => {
      const lector = new FileReader();
      lector.onload = () => resolve(String(lector.result));
      lector.onerror = () => reject(new Error('No se pudo leer el archivo.'));
      lector.readAsDataURL(archivo);
    });
  }
}
