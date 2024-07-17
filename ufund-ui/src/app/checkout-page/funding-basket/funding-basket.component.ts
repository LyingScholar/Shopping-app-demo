import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Need } from '../../needs-page/needs-list/need';
import { UserService } from '../../user.service';
import { NeedComponent } from './need/need.component';
import { CommonModule } from '@angular/common';
import { LinloutService } from '../../linlout.service';
import { User } from '../../user';

@Component({
  selector: 'app-funding-basket',
  standalone: true,
  imports: [NeedComponent,CommonModule],
  templateUrl: './funding-basket.component.html',
  styleUrl: './funding-basket.component.css'
})
export class FundingBasketComponent implements OnInit {

  user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };
  userId: number = 0;
  needs: Need[] = [];
  errorMessage: string = '';


  constructor (private userService: UserService,private linloutService: LinloutService) {
    this.linloutService.user$.subscribe((user: User) => {
      this.user = user;
      this.userId = user.id;
    })
  }

  async ngOnInit(): Promise<void> {
    await this.userService.viewFundingBasket(this.userId).then(needs => {
      this.needs = needs;
    }, (error) => {
      console.error('Error getting needs', error);
      this.errorMessage = 'Error getting needs. Try again.';
    });
  }
}
