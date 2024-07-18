import { Component } from '@angular/core';
import { NeedService } from '../services/need.service';
import { Need } from '../need';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-need-page',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './edit-need-page.component.html',
  styleUrl: './edit-need-page.component.css'
})
export class EditNeedPageComponent {
  title = 'U-Fund Edit Needs Page'
  needId: number;
  needName: string = '';
  needType: string = '';
  needCost: number = 0;
  needQuantity: number = 0;

  constructor (private needService: NeedService,private router:Router) {
    this.needId = needService.getEdit();
  }

  async confirmEdits(): Promise<void> {
    var name = this.needName;
    var type = this.needType;
    var quantity = this.needQuantity;
    var cost = this.needCost;
    var id = this.needId;
    if (name == '' || type == '' || quantity < 1 || cost < 1 || id < 1) {
      return;
    }
    var need: Need = new class {
      name: string = name;
      type: string = type;
      quantity: number = quantity;
      cost: number = cost;
      id:number = id;
    }
    await this.needService.editNeed(need);
    this.router.navigate(['/home-page']);
  }
}
