import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniNeedBasketComponent } from './mini-need-basket.component';

describe('MiniNeedBasketComponent', () => {
  let component: MiniNeedBasketComponent;
  let fixture: ComponentFixture<MiniNeedBasketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiniNeedBasketComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MiniNeedBasketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
