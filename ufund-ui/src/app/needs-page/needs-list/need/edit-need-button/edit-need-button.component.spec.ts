import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNeedButtonComponent } from './edit-need-button.component';

describe('EditNeedButtonComponent', () => {
  let component: EditNeedButtonComponent;
  let fixture: ComponentFixture<EditNeedButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditNeedButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditNeedButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
