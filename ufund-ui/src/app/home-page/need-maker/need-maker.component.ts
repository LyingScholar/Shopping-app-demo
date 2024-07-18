import { Component } from '@angular/core';
import { LinloutService } from '../../services/linlout.service';

@Component({
  selector: 'app-need-maker',
  standalone: true,
  imports: [],
  templateUrl: './need-maker.component.html',
  styleUrl: './need-maker.component.css'
})
export class NeedMakerComponent {

  admin: boolean = false;

  constructor (private linloutService: LinloutService) {
    this.linloutService.admin$.subscribe((admin: boolean) => {
      this.admin = admin;
    });
  }

}
