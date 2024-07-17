import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { AddToBasketButtonComponent } from './add-to-basket-button/add-to-basket-button.component';

@Component({
  selector: 'app-need',
  standalone: true,
  imports: [AddToBasketButtonComponent],
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {
  @Input() need!: Need;
}
