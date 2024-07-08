import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LinloutService } from '../../linlout.service';

@Component({
  selector: 'app-login-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login-button.component.html',
  styleUrl: './login-button.component.css'
})
export class LoginButtonComponent {
  text: string = 'LOGIN';

  linWidth = {
    width: '65px',
  }
  loutWidth = {
    width: '75px',
  }

  constructor(private linloutService: LinloutService) {
    this.linloutService.text$.subscribe(text => {
      this.text = text;
    });
  }

  swapLinLout(): void {
    const newText = this.text === 'LOGIN' ? 'LOGOUT' : 'LOGIN';
    this.linloutService.setText(newText);
  }
}
