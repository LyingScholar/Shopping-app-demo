import { Component } from '@angular/core';
import { NeedService } from '../services/need.service';
import { Need } from '../need';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-need-page',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './add-need-page.component.html',
  styleUrl: './add-need-page.component.css'
})
export class AddNeedPageComponent {
  title = 'U-Fund Edit Needs Page'
  needName: string = '';
  needType: string = '';
  needCost: number = 0;
  needQuantity: number = 0;

  constructor (private needService: NeedService,private router:Router) {
  }

  async confirmNeed(): Promise<void> {
    var name = this.needName;
    var type = this.needType;
    var quantity = this.needQuantity;
    var cost = this.needCost;
    if (name == '' || type == '' || quantity < 1 || cost < 1) {
      return;
    }
    var need: Need = new class {
      name: string = name;
      type: string = type;
      quantity: number = quantity;
      cost: number = cost;
      id:number = 0;
    }
    await this.needService.makeNeed(need);
    this.router.navigate(['/home-page']);
  }
}
