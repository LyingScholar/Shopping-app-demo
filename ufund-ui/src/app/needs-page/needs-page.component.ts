import { Component } from '@angular/core';
import { LoginButtonComponent } from '../login-button/login-button.component';
import { MiniNeedBasketComponent } from '../mini-need-basket/mini-need-basket.component';
import { NeedsListComponent } from './needs-list/needs-list.component';
import { SearchBarComponent } from './search-bar/search-bar.component';

@Component({
  selector: 'app-needs-page',
  standalone: true,
  imports: [LoginButtonComponent, MiniNeedBasketComponent, NeedsListComponent, SearchBarComponent],
  templateUrl: './needs-page.component.html',
  styleUrl: './needs-page.component.css'
})


export class NeedsPageComponent {
  title = 'U-Fund Needs List';
}