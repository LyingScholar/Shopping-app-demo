import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom,BehaviorSubject, Observable } from 'rxjs';
import { Need } from '../need';

@Injectable({
  providedIn: 'root'
})
export class NeedService {

  private apiUrl = 'http://localhost:8080';

  searchSubject: BehaviorSubject<Need[]> = new BehaviorSubject<Need[]>([]);
  search$: Observable<Need[]> = this.searchSubject.asObservable();

  constructor(private http: HttpClient) { }

  async callGetNeeds(): Promise<Need[]> {
    try {
      const needs: Need[] = await firstValueFrom(
        this.http.get<Need[]>(`${this.apiUrl}/Needs`)
      );
      return needs;
    } catch (error) {
      console.error('Error', error);
      return [];
    }
  }

  async searchNeeds(text: string): Promise<void> {
    const needs: Need[] = await firstValueFrom(
      this.http.get<Need[]>(`${this.apiUrl}/Needs/?name=${text}`));
      this.searchSubject = new BehaviorSubject<Need[]>(needs);
      this.search$ = this.searchSubject.asObservable();
  }
}
