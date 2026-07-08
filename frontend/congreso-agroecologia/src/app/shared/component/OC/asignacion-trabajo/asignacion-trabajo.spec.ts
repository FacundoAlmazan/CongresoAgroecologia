import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignacionTrabajo } from './asignacion-trabajo';

describe('AsignacionTrabajo', () => {
  let component: AsignacionTrabajo;
  let fixture: ComponentFixture<AsignacionTrabajo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsignacionTrabajo],
    }).compileComponents();

    fixture = TestBed.createComponent(AsignacionTrabajo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
