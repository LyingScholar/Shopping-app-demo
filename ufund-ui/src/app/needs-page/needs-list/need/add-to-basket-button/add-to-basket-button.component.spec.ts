import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddToBasketButtonComponent } from './add-to-basket-button.component';

describe('AddToBasketButtonComponent', () => {
  let component: AddToBasketButtonComponent;
  let fixture: ComponentFixture<AddToBasketButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddToBasketButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddToBasketButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
