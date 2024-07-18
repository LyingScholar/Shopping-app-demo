import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { LinloutService } from '../services/linlout.service';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { Need } from '../need';
import { User } from '../user';
import { NeedPreviewComponent } from './need-preview/need-preview.component';
import { CheckoutButtonComponent } from './checkout-button/checkout-button.component';

@Component({
  selector: 'app-mini-need-basket',
  standalone: true,
  imports: [CommonModule,NeedPreviewComponent,CheckoutButtonComponent],
  templateUrl: './mini-need-basket.component.html',
  styleUrl: './mini-need-basket.component.css'
})
export class MiniNeedBasketComponent implements OnInit {
  title: string = '';
  loggedIn: boolean = false;
  errorMessage: string = '';
  needs: Need[] = [];
  user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };
  userId: number = 0;
  admin: boolean = false;

  constructor(private linloutService: LinloutService,private userService: UserService) {
    this.linloutService.text$.subscribe(text => {
      if(text == 'LOGIN') {
        this.title = 'Not Logged In';
        this.loggedIn = false;
      } else {
        this.title = 'Funding Basket';
        this.loggedIn = true;
      }
    })
    this.linloutService.user$.subscribe((user: User) => {
      this.user = user;
      this.userId = user.id;
    })
    this.linloutService.admin$.subscribe((admin: boolean) => {
      this.admin = admin;
    })
  }

  async ngOnInit(): Promise<void> {
    await this.userService.viewFundingBasket(this.userId).then(needs =>{
      this.needs = needs;
    }, (error) => {
      console.error('Error getting needs', error);
      this.errorMessage = 'Error getting needs. Try again.';
    });
  }
}
