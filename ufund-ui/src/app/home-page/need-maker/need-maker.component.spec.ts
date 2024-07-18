import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedMakerComponent } from './need-maker.component';

describe('NeedMakerComponent', () => {
  let component: NeedMakerComponent;
  let fixture: ComponentFixture<NeedMakerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeedMakerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NeedMakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
