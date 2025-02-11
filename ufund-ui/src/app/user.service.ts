import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

import { Observable, of } from 'rxjs';

import { User } from './user';
import { Need } from './needs-page/needs-list/need';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private URL = 'http://localhost:8080';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) {}

  async searchUsers(username: string): Promise<User[]> {
    if (!username.trim()) {
      return await firstValueFrom(of([]));
    }
    return await firstValueFrom(this.http.get<User[]>(`${this.URL}/?name=${username}`));
  }

  async viewFundingBasket(userId: number): Promise<Need[]> {
    if (!userId) {
      return await firstValueFrom(of([]));
    }
    return await firstValueFrom(this.http.get<Need[]>(`${this.URL}/Helper/fundingBasket/?userId=${userId}`));
  }

  async addNeed(userId: number,needId: number): Promise<void> {
    if (!userId || !needId) {
      return await firstValueFrom(of());
    }
    await firstValueFrom(this.http.put<Need>(`${this.URL}/Helper/fundingBasket/${userId}/${needId}`,{observe: "need"}));
  }

  async removeNeed(userId: number,needId: number): Promise<void> {
    if (!userId || !needId) {
      return await firstValueFrom(of());
    }
    await firstValueFrom(this.http.delete<Need>(`${this.URL}/Helper/fundingBasket/delete/${userId}/${needId}`));
  }

  async callCheckout(userId: number): Promise<Need> {
    if (!userId) {
      return await firstValueFrom(of());
    }
    return await firstValueFrom(this.http.put<Need>(`${this.URL}/Helper/checkout/${userId}`,{observe: "response"}));
  }
}

