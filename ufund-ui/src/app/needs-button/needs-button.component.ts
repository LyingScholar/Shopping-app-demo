import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-needs-button',
  standalone: true,
  imports: [],
  templateUrl: './needs-button.component.html',
  styleUrl: './needs-button.component.css'
})
export class NeedsButtonComponent {

  constructor (private router: Router) {
  }

  swapPage(): void {
    this.router.navigate(['/needs-page']);
  }
}
