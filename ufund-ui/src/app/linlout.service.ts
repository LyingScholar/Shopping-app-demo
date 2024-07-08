import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LinloutService {
  private textSubject = new BehaviorSubject<string>('LOGIN');
  text$ = this.textSubject.asObservable();

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }
  
  setText(newText: string): void {
    this.textSubject.next(newText);
  }

  callLogin(text: string): Observable<any> {
    return this.http.post('${this.apiUrl}login', text, {responseType: 'text'});
  }
}
