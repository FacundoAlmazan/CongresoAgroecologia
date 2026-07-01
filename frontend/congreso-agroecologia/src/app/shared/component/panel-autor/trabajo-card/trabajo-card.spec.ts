import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrabajoCard } from './trabajo-card';

describe('TrabajoCard', () => {
  let component: TrabajoCard;
  let fixture: ComponentFixture<TrabajoCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrabajoCard],
    }).compileComponents();

    fixture = TestBed.createComponent(TrabajoCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
