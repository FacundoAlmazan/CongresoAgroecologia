import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { ResultadoEvaluacion } from './resultado-evaluacion';
import { EvaluacionService } from '../../service/evaluacion.service';

describe('ResultadoEvaluacion', () => {
  let component: ResultadoEvaluacion;
  let fixture: ComponentFixture<ResultadoEvaluacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoEvaluacion],
      providers: [
        provideRouter([]),
        { provide: EvaluacionService, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ResultadoEvaluacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
