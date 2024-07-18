import { Component, Input } from '@angular/core';
import { Need } from '../../../need';
import { AddToBasketButtonComponent } from './add-to-basket-button/add-to-basket-button.component';
import { DeleteNeedButonComponent } from './delete-need-buton/delete-need-buton.component';
import { EditNeedButtonComponent } from './edit-need-button/edit-need-button.component';

@Component({
  selector: 'app-need',
  standalone: true,
  imports: [AddToBasketButtonComponent,DeleteNeedButonComponent,EditNeedButtonComponent],
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {
  @Input() need!: Need;
}
