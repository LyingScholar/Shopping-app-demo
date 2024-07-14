import { Component, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, takeUntil } from 'rxjs/operators';
import { Subject, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LinloutService } from '../../linlout.service';
import { HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-username-input',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './username-input.component.html',
  styleUrl: './username-input.component.css'
})


export class UsernameInputComponent implements OnDestroy {
  username: string = '';
  errorMessage: string = '';
  private destroy$ = new Subject<void>();

  constructor (
    private router: Router,
    private linlout: LinloutService
  ){}

  attemptLogin(): void {
    if (this.username.trim()) {
      this.loginWithJava(this.username);
    } else {
      this.errorMessage = 'Please enter a username';
    }
  }


  loginWithJava(username: string): void {
    this.errorMessage = '';
    if (this.linlout.callLogin(username)) {
      this.linlout.setText('LOGOUT');
      this.router.navigate(['/home-page']);
    } else {
      this.errorMessage = 'Login failed. Please try again.'
    }
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
