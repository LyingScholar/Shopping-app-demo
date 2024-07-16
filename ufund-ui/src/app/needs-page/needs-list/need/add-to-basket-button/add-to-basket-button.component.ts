import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../need';
import { LinloutService } from '../../../../linlout.service';
import { UserService } from '../../../../user.service';
import { User } from '../../../../user';

@Component({
  selector: 'app-add-to-basket-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './add-to-basket-button.component.html',
  styleUrl: './add-to-basket-button.component.css'
})
export class AddToBasketButtonComponent {

  @Input() need!: Need;
  user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };
  userId: number = 0;
  username: string = '';

  constructor (private linloutService: LinloutService,private userService: UserService) {
    this.linloutService.user$.subscribe((user: User) => {
      this.user = user;
      this.userId = user.id;
      this.username = user.name;
    })
  }

  addToBasket(): void {
    this.userService.addNeed(this.userId,this.need.id);
    window.location.reload();
    this.linloutService.callLogout(this.username);
  }

}
