import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveFromBasketButtonComponent } from './remove-from-basket-button.component';

describe('RemoveFromBasketButtonComponent', () => {
  let component: RemoveFromBasketButtonComponent;
  let fixture: ComponentFixture<RemoveFromBasketButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemoveFromBasketButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RemoveFromBasketButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
