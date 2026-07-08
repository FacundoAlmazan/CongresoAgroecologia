import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaAsignacion } from './vista-asignacion';

describe('VistaAsignacion', () => {
  let component: VistaAsignacion;
  let fixture: ComponentFixture<VistaAsignacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaAsignacion],
    }).compileComponents();

    fixture = TestBed.createComponent(VistaAsignacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
