import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministradorInscripciones } from './administrador-inscripciones';

describe('AdministradorInscripciones', () => {
  let component: AdministradorInscripciones;
  let fixture: ComponentFixture<AdministradorInscripciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministradorInscripciones],
    }).compileComponents();

    fixture = TestBed.createComponent(AdministradorInscripciones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
