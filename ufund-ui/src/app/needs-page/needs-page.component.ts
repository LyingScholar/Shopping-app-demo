import { Component } from '@angular/core';
import { LoginButtonComponent } from '../home-page/login-button/login-button.component';
import { MiniNeedBasketComponent } from '../home-page/mini-need-basket/mini-need-basket.component';
import { NeedsListComponent } from './needs-list/needs-list.component';

@Component({
  selector: 'app-needs-page',
  standalone: true,
  imports: [LoginButtonComponent,MiniNeedBasketComponent,NeedsListComponent],
  templateUrl: './needs-page.component.html',
  styleUrl: './needs-page.component.css'
})
export class NeedsPageComponent {
  title = 'U-Fund Needs List';
}
