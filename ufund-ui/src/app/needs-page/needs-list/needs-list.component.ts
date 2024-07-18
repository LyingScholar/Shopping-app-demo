import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Need } from '../../need';
import { NeedService } from '../../services/need.service';
import { NeedComponent } from './need/need.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-needs-list',
  standalone: true,
  imports: [NeedComponent,CommonModule],
  templateUrl: './needs-list.component.html',
  styleUrl: './needs-list.component.css'
})
export class NeedsListComponent implements OnInit{
  needs: Need[] = [];
  errorMessage: string = '';

  constructor (private needService: NeedService) {}

  async ngOnInit(): Promise<void> {
    await this.needService.callGetNeeds().then(needs => {
      this.needs = needs;
    }, (error) => {
      console.error('Error getting needs', error);
      this.errorMessage = 'Error getting needs. Try again.';
    });
  }
}
