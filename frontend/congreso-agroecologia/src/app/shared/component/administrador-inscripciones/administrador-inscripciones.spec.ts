import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministradorInscripciones } from './administrador-inscripciones';
import { PagoService } from '../../service/pago.service';
import { of, Subject } from 'rxjs';
import { Inscripcion } from '../../model/inscripcion.model';
import { EstadoPago } from '../../model/EstadoPago.enum.model';
import { provideRouter } from '@angular/router';

describe('AdministradorInscripciones', () => {
  let component: AdministradorInscripciones;
  let fixture: ComponentFixture<AdministradorInscripciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministradorInscripciones],
      providers: [
        provideRouter([]),
        { provide: PagoService, useValue: { listar: () => of([]) } }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AdministradorInscripciones);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('renderiza la respuesta HTTP asíncrona y termina la carga', async () => {
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
    const respuesta = new Subject<Inscripcion[]>();
    vi.spyOn(pagoService, 'listar').mockReturnValue(respuesta.asObservable());
    const fixtureConDatos = TestBed.createComponent(AdministradorInscripciones);

    fixtureConDatos.detectChanges();
    expect(fixtureConDatos.nativeElement.textContent).toContain('Cargando inscripciones');

    respuesta.next([inscripcion]);
    respuesta.complete();
    await fixtureConDatos.whenStable();

    expect(fixtureConDatos.nativeElement.textContent).toContain('24/07/2026');
    expect(fixtureConDatos.nativeElement.textContent).not.toContain('Cargando inscripciones');
  });
});
