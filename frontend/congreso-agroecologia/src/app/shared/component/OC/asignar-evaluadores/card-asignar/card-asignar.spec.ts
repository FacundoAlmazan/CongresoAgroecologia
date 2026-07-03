import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardAsignar } from './card-asignar';

describe('CardAsignar', () => {
  let component: CardAsignar;
  let fixture: ComponentFixture<CardAsignar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardAsignar],
    }).compileComponents();

    fixture = TestBed.createComponent(CardAsignar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
