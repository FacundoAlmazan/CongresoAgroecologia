import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoInscripcion } from './estado-inscripcion';
import { PagoService } from '../../service/pago.service';
import { of, Subject } from 'rxjs';
import { provideRouter } from '@angular/router';
import { Inscripcion } from '../../model/inscripcion.model';
import { EstadoPago } from '../../model/EstadoPago.enum.model';

describe('EstadoInscripcion', () => {
  let component: EstadoInscripcion;
  let fixture: ComponentFixture<EstadoInscripcion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoInscripcion],
      providers: [
        provideRouter([]),
        { provide: PagoService, useValue: { obtenerMia: () => of(null) } }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EstadoInscripcion);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('muestra la respuesta HTTP asíncrona y termina la carga', async () => {
    const inscripcion = {
      id: 19,
      usuario: {
        id: 76,
        nombre: 'Ana',
        apellido: 'Pérez',
        email: 'ana@example.com'
      },
      monto: 35000,
      fechaRegistro: [2026, 7, 24, 17, 8, 33, 999488000],
      tipoInscripcion: 'Estudiante de grado',
      requiereFactura: false,
      requiereAdecuacion: false,
      tieneAlergiaOIntolerancia: false,
      efectivo: false,
      estado: EstadoPago.PENDIENTE
    } as Inscripcion;
    const pagoService = TestBed.inject(PagoService);
    const respuesta = new Subject<Inscripcion | null>();
    vi.spyOn(pagoService, 'obtenerMia').mockReturnValue(respuesta.asObservable());
    const fixtureConDatos = TestBed.createComponent(EstadoInscripcion);

    fixtureConDatos.detectChanges();
    expect(fixtureConDatos.nativeElement.textContent).toContain('Consultando tu inscripción');

    respuesta.next(inscripcion);
    respuesta.complete();
    await fixtureConDatos.whenStable();

    expect(fixtureConDatos.nativeElement.textContent).toContain('24/07/2026');
    expect(fixtureConDatos.nativeElement.textContent).toContain('PENDIENTE');
    expect(fixtureConDatos.nativeElement.textContent).not.toContain('Consultando tu inscripción');
  });
});
