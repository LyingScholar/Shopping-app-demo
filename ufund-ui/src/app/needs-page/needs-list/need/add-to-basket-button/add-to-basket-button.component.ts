import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../need';
import { NeedService } from '../../../../need.service';
import { UserService } from '../../../../user.service';

@Component({
  selector: 'app-add-to-basket-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './add-to-basket-button.component.html',
  styleUrl: './add-to-basket-button.component.css'
})
export class AddToBasketButtonComponent {

  private need: Need;
  private needService: NeedService;
  private userService: UserService;

  constructor (need: Need,needService: NeedService,userService: UserService) {
    this.need = need;
    this.needService = needService;
    this.userService = userService;
  }

  addToBasket(): void {
    //call function to remove need from all needs
    //call function to add need to user's basket
    window.location.reload()//refresh page
    return 3; //throwing error so I don't forget to add these functions later
  }

}
