import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedPreviewComponent } from './need-preview.component';

describe('NeedPreviewComponent', () => {
  let component: NeedPreviewComponent;
  let fixture: ComponentFixture<NeedPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeedPreviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NeedPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
