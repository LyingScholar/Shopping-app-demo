import { Component, Input } from '@angular/core';
import { Need } from '../../needs-page/needs-list/need';
import { RemoveFromBasketButtonComponent } from './remove-from-basket-button/remove-from-basket-button.component';

@Component({
  selector: 'app-need-preview',
  standalone: true,
  imports: [RemoveFromBasketButtonComponent],
  templateUrl: './need-preview.component.html',
  styleUrl: './need-preview.component.css'
})
export class NeedPreviewComponent {
  @Input() need!: Need;
}
