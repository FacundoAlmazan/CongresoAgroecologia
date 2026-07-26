import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { Evaluaciones } from './evaluaciones';
import { EvaluacionService } from '../../service/evaluacion.service';

describe('Evaluaciones', () => {
  let component: Evaluaciones;
  let fixture: ComponentFixture<Evaluaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Evaluaciones],
      providers: [
        provideRouter([]),
        { provide: EvaluacionService, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Evaluaciones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
