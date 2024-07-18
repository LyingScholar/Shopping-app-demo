import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Need } from '../../need';
import { NeedService } from '../../services/need.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: '../search-bar/search-bar.component.html',
  styleUrl: '../search-bar/search-bar.component.css'
})

export class NeedsListComponent implements OnInit {
  @Input() searchTerm: string = '';
  needs: Need[] = [];
  filteredNeeds: Need[] = [];
  errorMessage: string = '';

  constructor(private needService: NeedService) {}

  async ngOnInit(): Promise<void> {
    await this.needService.callGetNeeds().then(needs => {
      this.needs = needs;
      this.filterNeeds();
    }, (error) => {
      console.error('Error getting needs', error);
      this.errorMessage = 'Error getting needs. Try again.';
    });
  }

  filterNeeds() {
    if (this.searchTerm) {
      this.filteredNeeds = this.needs.filter(need =>
        need.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        need.type.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.filteredNeeds = this.needs;
    }
  }

  ngOnChanges() {
    this.filterNeeds();
  }

  // export class SearchBarComponent {
  // searchTerm: string = '';
  // needs: Need[] = [];

  
  // constructor(private needService: NeedService,private router: Router) {
  //   this.needService.search$.subscribe((needs: Need[]) => {
  //     this.needs = needs;
  //   });
  // }

  // async callSearch(): Promise<void> {
  //   await this.needService.searchNeeds(this.searchTerm);
  //   //document.getElementById("needs-List").reload();
  // }
}