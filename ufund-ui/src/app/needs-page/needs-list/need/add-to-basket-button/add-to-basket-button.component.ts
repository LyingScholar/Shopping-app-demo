import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../../../need';
import { LinloutService } from '../../../../services/linlout.service';
import { UserService } from '../../../../services/user.service';
import { User } from '../../../../user';
import { Router } from '@angular/router';

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
  admin: boolean = false;
  loggedIn: boolean = false;

  constructor (private router: Router,private linloutService: LinloutService,private userService: UserService) {
    this.linloutService.user$.subscribe((user: User) => {
      this.user = user;
      this.userId = user.id;
      this.username = user.name;
    })
    this.linloutService.text$.subscribe(text => {
      if(text == 'LOGIN') {
        this.loggedIn = false;
      } else {
        this.loggedIn = true;
      }
    })
    this.linloutService.admin$.subscribe((admin: boolean) => {
      this.admin = admin;
    })
  }

  addToBasket(): void {
    this.userService.addNeed(this.userId,this.need.id);
    //window.location.reload()
    this.router.navigate(['/home-page']);
    //this.linloutService.callLogout(this.username);
  }

}
