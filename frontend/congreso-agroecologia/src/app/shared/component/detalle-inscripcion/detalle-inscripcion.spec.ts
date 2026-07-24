import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleInscripcion } from './detalle-inscripcion';

describe('DetalleInscripcion', () => {
  let component: DetalleInscripcion;
  let fixture: ComponentFixture<DetalleInscripcion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleInscripcion],
    }).compileComponents();

    fixture = TestBed.createComponent(DetalleInscripcion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
