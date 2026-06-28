import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelEvaluaciones } from './panel-evaluaciones';

describe('PanelEvaluaciones', () => {
  let component: PanelEvaluaciones;
  let fixture: ComponentFixture<PanelEvaluaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelEvaluaciones],
    }).compileComponents();

    fixture = TestBed.createComponent(PanelEvaluaciones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
