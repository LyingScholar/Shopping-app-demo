import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeNeedButtonComponent } from './make-need-button.component';

describe('MakeNeedButtonComponent', () => {
  let component: MakeNeedButtonComponent;
  let fixture: ComponentFixture<MakeNeedButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MakeNeedButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MakeNeedButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
