import { Component } from '@angular/core';
import { LinloutService } from '../../linlout.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mini-need-basket',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mini-need-basket.component.html',
  styleUrl: './mini-need-basket.component.css'
})
export class MiniNeedBasketComponent {
  title: string = '';
  loggedIn: boolean = false;

  constructor(private linloutService: LinloutService) {
    this.linloutService.text$.subscribe(text => {
      if(text == 'LOGIN') {
        this.title = 'Not Logged In';
        this.loggedIn = false;
      } else {
        this.title = 'Funding Basket';
        this.loggedIn = true;
      }
    })
  }
}
