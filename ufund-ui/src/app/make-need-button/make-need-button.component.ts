import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LinloutService } from '../services/linlout.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-make-need-button',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './make-need-button.component.html',
  styleUrl: './make-need-button.component.css'
})
export class MakeNeedButtonComponent {
  admin:boolean = false;

  constructor (private router: Router,private linloutService: LinloutService) {
    this.linloutService.admin$.subscribe((admin: boolean) => {
      this.admin = admin;
    })
  }

  swapPage(): void {
    this.router.navigate(['/add-need-page']);
  }
}
