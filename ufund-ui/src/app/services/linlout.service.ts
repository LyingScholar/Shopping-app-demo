import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { User } from '../user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LinloutService {
  private textSubject: BehaviorSubject<string> = new BehaviorSubject<string>('LOGIN');
  text$: Observable<string> = this.textSubject.asObservable();

  private adminSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  admin$: Observable<boolean> = this.adminSubject.asObservable();


  private apiUrl = 'http://localhost:8080';

  private userSubject: BehaviorSubject<User> = new BehaviorSubject<User>(new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  });
  user$: Observable<User> = this.userSubject.asObservable();

  constructor(private http: HttpClient,private router: Router) { }
  
  setText(newText: string): void {
    this.textSubject.next(newText);
  }

  async callLogin(text: string): Promise<User> {
    var tempUser;
    tempUser =  await firstValueFrom(
      this.http.get<User>(`${this.apiUrl}/Users/login/?username=${text}`)
    );
    this.userSubject = new BehaviorSubject<User>(tempUser);
    this.user$ = this.userSubject.asObservable();
    this.adminSubject = new BehaviorSubject<boolean>(tempUser.admin);
    this.admin$ = this.adminSubject.asObservable();
    return tempUser;
  }

  async callLogout(text: string): Promise<void> {
    await firstValueFrom(
      this.http.get<any>(`${this.apiUrl}/Users/logout/?username=${text}`)
    );
    this.setText('LOGIN');
    this.adminSubject = new BehaviorSubject<boolean>(false);
    this.admin$ = this.adminSubject.asObservable();
    this.router.navigate(['/home-page']);
  }
}
