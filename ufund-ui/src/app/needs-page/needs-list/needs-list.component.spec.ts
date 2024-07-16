import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsListComponent } from './needs-list.component';

describe('NeedsListComponent', () => {
  let component: NeedsListComponent;
  let fixture: ComponentFixture<NeedsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeedsListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NeedsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
