import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionCongreso } from './inscripcion-congreso';

describe('InscripcionCongreso', () => {
  let component: InscripcionCongreso;
  let fixture: ComponentFixture<InscripcionCongreso>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InscripcionCongreso],
    }).compileComponents();

    fixture = TestBed.createComponent(InscripcionCongreso);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
