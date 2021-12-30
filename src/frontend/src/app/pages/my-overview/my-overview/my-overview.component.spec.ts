import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyOverviewComponent } from './my-overview.component';

describe('MyOverviewComponent', () => {
  let component: MyOverviewComponent;
  let fixture: ComponentFixture<MyOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
