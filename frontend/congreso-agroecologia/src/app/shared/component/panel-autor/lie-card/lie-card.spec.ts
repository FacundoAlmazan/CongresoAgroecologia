import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LieCard } from './lie-card';

describe('LieCard', () => {
  let component: LieCard;
  let fixture: ComponentFixture<LieCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LieCard],
    }).compileComponents();

    fixture = TestBed.createComponent(LieCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
