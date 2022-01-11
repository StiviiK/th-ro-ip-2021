import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperitemComponent } from './paperitem.component';

describe('PaperitemComponent', () => {
  let component: PaperitemComponent;
  let fixture: ComponentFixture<PaperitemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaperitemComponent ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
