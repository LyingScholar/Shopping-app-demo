
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Need } from '../../need';
import { NeedService } from '../../services/need.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})

export class SearchBarComponent {
  searchTerm: string = '';
  needs: Need[] = [];

  constructor(private needService: NeedService,private router: Router) {
    this.needService.search$.subscribe((needs: Need[]) => {
      this.needs = needs;
    });
  }

  async callSearch(): Promise<void> {
    await this.needService.searchNeeds(this.searchTerm);
  }
}