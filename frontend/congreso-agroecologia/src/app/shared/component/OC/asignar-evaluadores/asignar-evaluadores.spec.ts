import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignarEvaluadores } from './asignar-evaluadores';

describe('AsignarEvaluadores', () => {
  let component: AsignarEvaluadores;
  let fixture: ComponentFixture<AsignarEvaluadores>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsignarEvaluadores],
    }).compileComponents();

    fixture = TestBed.createComponent(AsignarEvaluadores);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
