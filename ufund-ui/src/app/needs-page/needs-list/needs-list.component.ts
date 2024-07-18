import { Component, Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { Need } from './need';
import { NeedService } from '../../need.service';
import { NeedComponent } from './need/need.component';
import { CommonModule } from '@angular/common';
import { LoginButtonComponent } from "../../login-button/login-button.component";
@Component({
  selector: 'app-needs-list',
  standalone: true,
  imports: [NeedComponent, CommonModule, LoginButtonComponent],
  templateUrl: './needs-list.component.html',
  styleUrl: './needs-list.component.css'
})
export class NeedsListComponent implements OnInit{
  @Input() searchTerm: string = '';
  needs: Need[] = [];
  errorMessage: string = '';
  filteredNeeds: Need[] = [];


  constructor (private needService: NeedService) {}

  async ngOnInit(): Promise<void> {
    await this.needService.callGetNeeds().then(needs => {
      this.needs = needs;
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
}