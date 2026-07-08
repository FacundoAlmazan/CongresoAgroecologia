import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlCard } from './control-card';

describe('ControlCard', () => {
  let component: ControlCard;
  let fixture: ComponentFixture<ControlCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControlCard],
    }).compileComponents();

    fixture = TestBed.createComponent(ControlCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
