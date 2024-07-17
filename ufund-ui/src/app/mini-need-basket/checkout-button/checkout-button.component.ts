import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout-button',
  standalone: true,
  imports: [],
  templateUrl: './checkout-button.component.html',
  styleUrl: './checkout-button.component.css'
})
export class CheckoutButtonComponent {

  constructor (private router: Router) {
  }

  swapPage(): void {
    this.router.navigate(['/checkout-page']);
  }
}
