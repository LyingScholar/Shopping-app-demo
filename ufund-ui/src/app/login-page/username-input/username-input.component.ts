import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LinloutService } from '../../linlout.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-username-input',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './username-input.component.html',
  styleUrl: './username-input.component.css'
})
export class UsernameInputComponent {
  username: string = '';

  constructor (private router: Router,private linloutService: LinloutService) {}

  attemptLogin() {
    this.loginWithJava(this.username);
  }

  loginWithJava(text: string) {
    this.linloutService.callLogin(text).subscribe(response => {
      console.log(response);
      if (response == 200) {
        this.router.navigate(['/home-page']);
      }
    });
  }
}
