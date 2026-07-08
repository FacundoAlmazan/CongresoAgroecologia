import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoPaper } from './info-paper';

describe('InfoPaper', () => {
  let component: InfoPaper;
  let fixture: ComponentFixture<InfoPaper>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoPaper],
    }).compileComponents();

    fixture = TestBed.createComponent(InfoPaper);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
