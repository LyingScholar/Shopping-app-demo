import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LinloutService } from '../linlout.service';
import { Router } from '@angular/router';
import { User } from '../user';

@Component({
  selector: 'app-login-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login-button.component.html',
  styleUrl: './login-button.component.css'
})
export class LoginButtonComponent {
  text: string = 'LOGIN';
  user: User = new class {
    name: string = '';
    id: number = 0;
    admin: boolean = false;
  }
  username: string = '';

  linWidth = {
    width: '65px',
  }
  loutWidth = {
    width: '75px',
  }

  constructor(private linloutService: LinloutService, private router: Router) {
    this.linloutService.text$.subscribe(text => {
      this.text = text;
    });
    this.linloutService.user$.subscribe(user => {
      this.user = user;
      this.username = user.name;
    });
  }

  swapPage(): void {
    if(this.text == 'LOGIN') {
      this.router.navigate(['/login-page']);
    } else {
      this.linloutService.callLogout(this.username);
    }
  }
}
