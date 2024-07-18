import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../../needs-page/needs-list/need';
import { LinloutService } from '../../../linlout.service';
import { UserService } from '../../../user.service';
import { User } from '../../../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-remove-from-basket-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './remove-from-basket-button.component.html',
  styleUrl: './remove-from-basket-button.component.css'
})
export class RemoveFromBasketButtonComponent {
  @Input() need!: Need;
  user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };
  username: string = '';

  constructor (private router:Router, private linloutService: LinloutService,private userService: UserService) {
    this.linloutService.user$.subscribe(user => {
      this.user = user;
      this.username = user.name;
    })
  }

  removeFromBasket(): void {
    this.userService.removeNeed(this.user.id,this.need.id);
    //window.location.reload()
    this.router.navigate(['/home-page']);
    //this.linloutService.callLogout(this.username);
  }
}
