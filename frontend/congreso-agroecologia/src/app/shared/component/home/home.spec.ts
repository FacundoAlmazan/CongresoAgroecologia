import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Home } from './home';
import { provideRouter } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { PagoService } from '../../service/pago.service';
import { of, Subject } from 'rxjs';
import { Inscripcion } from '../../model/inscripcion.model';
import { EstadoPago } from '../../model/EstadoPago.enum.model';
import { UsuarioAutenticado } from '../../model/auth.model';

describe('Home', () => {
  let component: Home;
  let fixture: ComponentFixture<Home>;

  beforeEach(async () => {
    history.replaceState({}, '');
    await TestBed.configureTestingModule({
      imports: [Home],
      providers: [
        provideRouter([]),
        {
          provide: AuthService,
          useValue: {
            getUsuarioActual: () => null,
            estaLogueado: () => false
          }
        },
        { provide: PagoService, useValue: { obtenerMia: () => of(null) } }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('muestra y consume la confirmación recibida al navegar', async () => {
    const mensaje = 'Tu inscripción se registró correctamente y quedó pendiente de revisión.';
    history.replaceState({ mensajeInscripcion: mensaje }, '');
    const fixtureConMensaje = TestBed.createComponent(Home);

    await fixtureConMensaje.whenStable();

    const alerta = fixtureConMensaje.nativeElement.querySelector('[role="status"]');
    expect(alerta?.textContent).toContain('Inscripción enviada');
    expect(alerta?.textContent).toContain(mensaje);
    expect(history.state.mensajeInscripcion).toBeUndefined();

    alerta.querySelector('button').click();
    fixtureConMensaje.detectChanges();
    expect(fixtureConMensaje.nativeElement.querySelector('[role="status"]')).toBeNull();
  });

  it('actualiza el estado de inscripción después de una respuesta HTTP asíncrona', async () => {
    const usuario: UsuarioAutenticado = {
      id: 76,
      nombre: 'Ana',
      apellido: 'Pérez',
      email: 'ana@example.com',
      activo: true,
      roles: ['PARTICIPANTE']
    };
    const inscripcion = {
      id: 19,
      usuario: { id: 76, nombre: 'Ana', apellido: 'Pérez', email: 'ana@example.com' },
      monto: 35000,
      requiereFactura: false,
      requiereAdecuacion: false,
      tieneAlergiaOIntolerancia: false,
      efectivo: false,
      estado: EstadoPago.PENDIENTE
    } as Inscripcion;
    const authService = TestBed.inject(AuthService);
    const pagoService = TestBed.inject(PagoService);
    const respuesta = new Subject<Inscripcion | null>();
    vi.spyOn(authService, 'getUsuarioActual').mockReturnValue(usuario);
    vi.spyOn(authService, 'estaLogueado').mockReturnValue(true);
    vi.spyOn(pagoService, 'obtenerMia').mockReturnValue(respuesta.asObservable());
    const fixtureLogueado = TestBed.createComponent(Home);

    fixtureLogueado.detectChanges();
    respuesta.next(inscripcion);
    respuesta.complete();
    await fixtureLogueado.whenStable();

    expect(fixtureLogueado.nativeElement.textContent).toContain('Pendiente de revisión');
    expect(fixtureLogueado.nativeElement.textContent).toContain('Ver mi inscripción');
  });
});
