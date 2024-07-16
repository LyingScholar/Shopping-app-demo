import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalizeButtonComponent } from './finalize-button.component';

describe('FinalizeButtonComponent', () => {
  let component: FinalizeButtonComponent;
  let fixture: ComponentFixture<FinalizeButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinalizeButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FinalizeButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
