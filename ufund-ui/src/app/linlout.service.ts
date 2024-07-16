import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class LinloutService {
  private textSubject = new BehaviorSubject<string>('LOGIN');
  text$ = this.textSubject.asObservable();


  private apiUrl = 'http://localhost:8080';

  public user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };

  constructor(private http: HttpClient) { }
  
  setText(newText: string): void {
    this.textSubject.next(newText);
  }

  async callLogin(text: string): Promise<User> {
    try {
      this.user =  await firstValueFrom(
        this.http.get<User>(`${this.apiUrl}/Users/login/?username=${text}`)
      );
      return this.user;
    } catch (error) {
      console.error('Error', error);
      throw new Error("Internal Error");
    }
  }
}
