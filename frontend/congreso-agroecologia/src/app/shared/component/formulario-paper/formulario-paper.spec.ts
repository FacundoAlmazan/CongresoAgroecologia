import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioPaper } from './formulario-paper';

describe('FormularioPaper', () => {
  let component: FormularioPaper;
  let fixture: ComponentFixture<FormularioPaper>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioPaper],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioPaper);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
