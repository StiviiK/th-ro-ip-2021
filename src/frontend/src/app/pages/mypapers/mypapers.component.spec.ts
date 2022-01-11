import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MypapersComponent } from './mypapers.component';

describe('MypapersComponent', () => {
  let component: MypapersComponent;
  let fixture: ComponentFixture<MypapersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MypapersComponent ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MypapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
