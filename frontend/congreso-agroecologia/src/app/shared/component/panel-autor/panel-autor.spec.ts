import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelAutor } from './panel-autor';

describe('PanelAutor', () => {
  let component: PanelAutor;
  let fixture: ComponentFixture<PanelAutor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelAutor],
    }).compileComponents();

    fixture = TestBed.createComponent(PanelAutor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
