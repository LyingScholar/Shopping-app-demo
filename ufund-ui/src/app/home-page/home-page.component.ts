import { Component } from '@angular/core';
import { LoginButtonComponent } from './login-button/login-button.component';
import { MiniNeedBasketComponent } from './mini-need-basket/mini-need-basket.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [LoginButtonComponent, MiniNeedBasketComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  title = 'U-Fund Home Page';
}
