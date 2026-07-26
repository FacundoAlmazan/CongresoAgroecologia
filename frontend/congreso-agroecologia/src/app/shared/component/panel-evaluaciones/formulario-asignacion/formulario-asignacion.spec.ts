import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { FormularioAsignacion } from './formulario-asignacion';
import { AsignacionService } from '../../../service/asignacion.service';

describe('FormularioAsignacion', () => {
  let component: FormularioAsignacion;
  let fixture: ComponentFixture<FormularioAsignacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioAsignacion],
      providers: [
        provideRouter([]),
        { provide: AsignacionService, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioAsignacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
