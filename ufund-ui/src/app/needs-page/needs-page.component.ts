import { Component } from '@angular/core';
import { LoginButtonComponent } from '../login-button/login-button.component';
import { MiniNeedBasketComponent } from '../mini-need-basket/mini-need-basket.component';
import { NeedsListComponent } from './needs-list/needs-list.component';
import { SearchBarComponent } from './search-bar/search-bar.component';

@Component({
  selector: 'app-needs-page',
  standalone: true,
<<<<<<< HEAD
  imports: [LoginButtonComponent, MiniNeedBasketComponent, NeedsListComponent, SearchBarComponent],
=======
  imports: [LoginButtonComponent,MiniNeedBasketComponent,NeedsListComponent,SearchBarComponent],
>>>>>>> b3b7ee152bc768bcc084e1f7da91040e35af408c
  templateUrl: './needs-page.component.html',
  styleUrl: './needs-page.component.css'
})


export class NeedsPageComponent {
  title = 'U-Fund Needs List';

  onSearch(searchTerm: string) {
    // Implement search logic here
    console.log('Searching for:', searchTerm);
  }
}