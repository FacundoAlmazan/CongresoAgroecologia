import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { FormularioEvaluacion } from './formulario-evaluacion';
import { AuthService } from '../../service/auth.service';
import { EvaluacionService } from '../../service/evaluacion.service';
import { TrabajoService } from '../../service/trabajo.service';

describe('FormularioEvaluacion', () => {
  let component: FormularioEvaluacion;
  let fixture: ComponentFixture<FormularioEvaluacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioEvaluacion],
      providers: [
        provideRouter([]),
        {
          provide: AuthService,
          useValue: { getUsuarioActual: () => ({ id: 7 }) },
        },
        { provide: EvaluacionService, useValue: {} },
        { provide: TrabajoService, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioEvaluacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
