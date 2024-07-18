import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Need } from '../need';

@Injectable({
  providedIn: 'root'
})
export class NeedService {

  private apiUrl = 'http://localhost:8080';

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
}
