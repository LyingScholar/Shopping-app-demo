import { Component } from '@angular/core';
import { FinalizeButtonComponent } from './finalize-button/finalize-button.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';

@Component({
  selector: 'app-checkout-page',
  standalone: true,
  imports: [FinalizeButtonComponent,FundingBasketComponent],
  templateUrl: './checkout-page.component.html',
  styleUrl: './checkout-page.component.css'
})
export class CheckoutPageComponent {
  title = "U-Fund Checkout Page";
}
