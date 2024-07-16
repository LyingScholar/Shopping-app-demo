import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsButtonComponent } from './needs-button.component';

describe('NeedsButtonComponent', () => {
  let component: NeedsButtonComponent;
  let fixture: ComponentFixture<NeedsButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeedsButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NeedsButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
