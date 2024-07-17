import { Component } from '@angular/core';
import { LoginButtonComponent } from '../login-button/login-button.component';
import { MiniNeedBasketComponent } from '../mini-need-basket/mini-need-basket.component';
import { NeedsButtonComponent } from '../needs-button/needs-button.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [LoginButtonComponent, MiniNeedBasketComponent,NeedsButtonComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  title = 'U-Fund Home Page';
}
