<<<<<<< HEAD
import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
=======
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Need } from '../../need';
import { NeedService } from '../../services/need.service';
<<<<<<< HEAD
import { Router } from '@angular/router';
=======
>>>>>>> b3b7ee152bc768bcc084e1f7da91040e35af408c
>>>>>>> 8d9514615278034499dff9db8b17744589e3a596

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})

<<<<<<< HEAD


export class SearchBarComponent {
  searchTerm: string = '';
  @Output() search = new EventEmitter<string>();

  onSearch() {
    this.search.emit(this.searchTerm);
=======
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
<<<<<<< HEAD
    //document.getElementById("needs-List").reload();
=======
>>>>>>> b3b7ee152bc768bcc084e1f7da91040e35af408c
>>>>>>> 8d9514615278034499dff9db8b17744589e3a596
  }
}