import { Component } from '@angular/core';
import {UsernameInputComponent} from './username-input/username-input.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [UsernameInputComponent,FormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  title = 'U-Fund Login Page';
}
