import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { VistaAsignacion } from './vista-asignacion';
import { AsignacionService } from '../../../service/asignacion.service';
import { AuthService } from '../../../service/auth.service';

describe('VistaAsignacion', () => {
  let component: VistaAsignacion;
  let fixture: ComponentFixture<VistaAsignacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaAsignacion],
      providers: [
        provideRouter([]),
        {
          provide: AuthService,
          useValue: { getUsuarioActual: () => ({ id: 7 }) },
        },
        {
          provide: AsignacionService,
          useValue: { listarMias: () => of([]) },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(VistaAsignacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
