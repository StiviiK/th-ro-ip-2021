import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpapersdialogComponent } from './addpapersdialog.component';

describe('AddpapersdialogComponent', () => {
  let component: AddpapersdialogComponent;
  let fixture: ComponentFixture<AddpapersdialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddpapersdialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpapersdialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
