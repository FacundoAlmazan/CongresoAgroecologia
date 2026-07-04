import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoEvaluacion } from './resultado-evaluacion';

describe('ResultadoEvaluacion', () => {
  let component: ResultadoEvaluacion;
  let fixture: ComponentFixture<ResultadoEvaluacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoEvaluacion],
    }).compileComponents();

    fixture = TestBed.createComponent(ResultadoEvaluacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
