import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleInscripcion } from './detalle-inscripcion';
import { PagoService } from '../../service/pago.service';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { of, Subject } from 'rxjs';
import { EstadoPago } from '../../model/EstadoPago.enum.model';
import { Inscripcion } from '../../model/inscripcion.model';

describe('DetalleInscripcion', () => {
  let component: DetalleInscripcion;
  let fixture: ComponentFixture<DetalleInscripcion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleInscripcion],
      providers: [
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: { get: () => '1' } } }
        },
        {
          provide: PagoService,
          useValue: {
            obtener: () => of({
              id: 1,
              usuario: { nombre: 'Ana', apellido: 'Pérez', email: 'ana@test.com' },
              monto: 100,
              montoAbonado: 100,
              requiereFactura: false,
              requiereAdecuacion: false,
              tieneAlergiaOIntolerancia: false,
              efectivo: false,
              estado: EstadoPago.PENDIENTE
            })
          }
        }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DetalleInscripcion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('muestra el detalle después de una respuesta HTTP asíncrona', async () => {
    const inscripcion = {
      id: 19,
      usuario: { id: 76, nombre: 'Ana', apellido: 'Pérez', email: 'ana@example.com' },
      monto: 35000,
      montoAbonado: 35000,
      requiereFactura: false,
      requiereAdecuacion: false,
      tieneAlergiaOIntolerancia: false,
      efectivo: false,
      estado: EstadoPago.PENDIENTE
    } as Inscripcion;
    const pagoService = TestBed.inject(PagoService);
    const respuesta = new Subject<Inscripcion>();
    vi.spyOn(pagoService, 'obtener').mockReturnValue(respuesta.asObservable());
    const fixtureAsincrono = TestBed.createComponent(DetalleInscripcion);

    fixtureAsincrono.detectChanges();
    respuesta.next(inscripcion);
    respuesta.complete();
    await fixtureAsincrono.whenStable();

    expect(fixtureAsincrono.nativeElement.textContent).toContain('Ana Pérez');
    expect(fixtureAsincrono.nativeElement.textContent).not.toContain('Cargando detalle');
  });
});
