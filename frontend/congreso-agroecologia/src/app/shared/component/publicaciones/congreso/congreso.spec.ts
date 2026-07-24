import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Congreso } from './congreso';

describe('Congreso', () => {
  let component: Congreso;
  let fixture: ComponentFixture<Congreso>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Congreso],
    }).compileComponents();

    fixture = TestBed.createComponent(Congreso);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
