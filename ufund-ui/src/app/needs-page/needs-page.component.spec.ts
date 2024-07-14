import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsPageComponent } from './needs-page.component';

describe('NeedsPageComponent', () => {
  let component: NeedsPageComponent;
  let fixture: ComponentFixture<NeedsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeedsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NeedsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
