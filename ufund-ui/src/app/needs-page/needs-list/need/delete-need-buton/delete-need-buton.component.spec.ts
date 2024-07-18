import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteNeedButonComponent } from './delete-need-buton.component';

describe('DeleteNeedButonComponent', () => {
  let component: DeleteNeedButonComponent;
  let fixture: ComponentFixture<DeleteNeedButonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteNeedButonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteNeedButonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
