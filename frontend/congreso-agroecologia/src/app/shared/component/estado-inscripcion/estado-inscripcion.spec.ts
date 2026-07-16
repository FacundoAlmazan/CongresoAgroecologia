import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoInscripcion } from './estado-inscripcion';

describe('EstadoInscripcion', () => {
  let component: EstadoInscripcion;
  let fixture: ComponentFixture<EstadoInscripcion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoInscripcion],
    }).compileComponents();

    fixture = TestBed.createComponent(EstadoInscripcion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
