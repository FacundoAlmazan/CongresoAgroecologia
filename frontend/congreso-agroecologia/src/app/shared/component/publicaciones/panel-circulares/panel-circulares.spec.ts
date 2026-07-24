import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelCirculares } from './panel-circulares';

describe('PanelCirculares', () => {
  let component: PanelCirculares;
  let fixture: ComponentFixture<PanelCirculares>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelCirculares],
    }).compileComponents();

    fixture = TestBed.createComponent(PanelCirculares);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
