import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluacionTrabajo } from './evaluacion-trabajo';

describe('EvaluacionTrabajo', () => {
  let component: EvaluacionTrabajo;
  let fixture: ComponentFixture<EvaluacionTrabajo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluacionTrabajo],
    }).compileComponents();

    fixture = TestBed.createComponent(EvaluacionTrabajo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
