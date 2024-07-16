import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../need';
import { LinloutService } from '../../../../linlout.service';
import { UserService } from '../../../../user.service';

@Component({
  selector: 'app-add-to-basket-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './add-to-basket-button.component.html',
  styleUrl: './add-to-basket-button.component.css'
})
export class AddToBasketButtonComponent {

  @Input() need!: Need;
  private linloutService: LinloutService;
  private userService: UserService;

  constructor (linloutService: LinloutService,userService: UserService) {
    this.linloutService = linloutService;
    this.userService = userService;
  }

  setNeed(need: Need): void {
    this.need = need;
  }

  addToBasket(): void {
    this.userService.addNeed(this.linloutService.user.id,this.need.id);
    window.location.reload()
  }

}
