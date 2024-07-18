import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNeedPageComponent } from './edit-need-page.component';

describe('EditNeedPageComponent', () => {
  let component: EditNeedPageComponent;
  let fixture: ComponentFixture<EditNeedPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditNeedPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditNeedPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
