import { Component, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, takeUntil } from 'rxjs/operators';
import { Subject, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LinloutService } from '../../services/linlout.service';
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


  async loginWithJava(username: string): Promise<void> {
    this.errorMessage = '';
    try {
      await this.linlout.callLogin(username);
    } catch (error) {
      this.errorMessage = "Error: " + error;
      return;
    }
    this.linlout.setText('LOGOUT');
    this.router.navigate(['/home-page']);
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
