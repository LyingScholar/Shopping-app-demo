<<<<<<< HEAD
import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
=======
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Need } from '../../need';
import { NeedService } from '../../services/need.service';
>>>>>>> b3b7ee152bc768bcc084e1f7da91040e35af408c

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

  constructor(private needService: NeedService) {
    this.needService.search$.subscribe((needs: Need[]) => {
      this.needs = needs;
    });
  }

  async callSearch(): Promise<void> {
    await this.needService.searchNeeds(this.searchTerm);
>>>>>>> b3b7ee152bc768bcc084e1f7da91040e35af408c
  }
}