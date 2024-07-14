import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LinloutService {
  private textSubject = new BehaviorSubject<string>('LOGIN');
  text$ = this.textSubject.asObservable();

  private latestResponseStatus = 0;

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }
  
  setText(newText: string): void {
    this.textSubject.next(newText);
  }

  async callLogin(text: string): Promise<number> {
    try {
      const response: HttpResponse<any> = await firstValueFrom(
        this.http.post<any>(`${this.apiUrl}/User/login/?username=${text}`, {observe: 'response' })
      );
      return response.status;
    } catch (error) {
      console.error('Error', error);
      return 500;
    }
  }
}
