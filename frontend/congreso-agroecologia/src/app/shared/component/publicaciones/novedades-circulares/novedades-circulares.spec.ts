import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovedadesCirculares } from './novedades-circulares';

describe('NovedadesCirculares', () => {
  let component: NovedadesCirculares;
  let fixture: ComponentFixture<NovedadesCirculares>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovedadesCirculares],
    }).compileComponents();

    fixture = TestBed.createComponent(NovedadesCirculares);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
