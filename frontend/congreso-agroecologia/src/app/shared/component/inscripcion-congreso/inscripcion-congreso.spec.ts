import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionCongreso } from './inscripcion-congreso';
import { PagoService } from '../../service/pago.service';
import { AuthService } from '../../service/auth.service';
import { of, Subject } from 'rxjs';
import { provideRouter, Router } from '@angular/router';
import { UsuarioAutenticado } from '../../model/auth.model';

describe('InscripcionCongreso', () => {
  let component: InscripcionCongreso;
  let fixture: ComponentFixture<InscripcionCongreso>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InscripcionCongreso],
      providers: [
        provideRouter([]),
        {
          provide: PagoService,
          useValue: { obtenerMia: () => of(null), registrar: () => of(null), reenviar: () => of(null) }
        },
        {
          provide: AuthService,
          useValue: { getUsuarioActual: () => null }
        }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(InscripcionCongreso);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('navega al home con una confirmación después de registrar', () => {
    const authService = TestBed.inject(AuthService);
    const router = TestBed.inject(Router);
    const usuario: UsuarioAutenticado = {
      id: 76,
      nombre: 'Ana',
      apellido: 'Pérez',
      email: 'ana@example.com',
      activo: true,
      roles: ['PARTICIPANTE']
    };
    vi.spyOn(authService, 'getUsuarioActual').mockReturnValue(usuario);
    const navigate = vi.spyOn(router, 'navigate').mockReturnValue(new Promise(() => {}));
    component.efectivo = true;

    component.finalizarInscripcion();

    expect(component.cargando).toBe(false);
    expect(navigate).toHaveBeenCalledWith(['/home'], {
      state: {
        mensajeInscripcion: 'Tu inscripción se registró correctamente y quedó pendiente de revisión.'
      }
    });
  });

  it('muestra un error recibido de forma asíncrona al registrar', async () => {
    const authService = TestBed.inject(AuthService);
    const pagoService = TestBed.inject(PagoService);
    const usuario: UsuarioAutenticado = {
      id: 76,
      nombre: 'Ana',
      apellido: 'Pérez',
      email: 'ana@example.com',
      activo: true,
      roles: ['PARTICIPANTE']
    };
    const respuesta = new Subject<never>();
    vi.spyOn(authService, 'getUsuarioActual').mockReturnValue(usuario);
    vi.spyOn(pagoService, 'registrar').mockReturnValue(respuesta.asObservable());
    component.efectivo = true;

    component.finalizarInscripcion();
    respuesta.error({ error: { error: 'No se pudo guardar la inscripción.' } });
    await fixture.whenStable();

    expect(component.cargando).toBe(false);
    expect(fixture.nativeElement.textContent).toContain('No se pudo guardar la inscripción.');
  });
});
