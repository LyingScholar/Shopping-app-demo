import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { User } from './user';
import { Need } from './needs-page/needs-list/need';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private URL = 'http://localhost:8080/heroes';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) {}

  searchUsers(username: string): Observable<User[]> {
    if (!username.trim()) {
      return of([]);
    }
    return this.http.get<User[]>(`${this.URL}/?name=${username}`);
  }

  viewFundingBasket(userId: number): Observable<Need[]> {
    if (!userId) {
      return of([]);
    }
    return this.http.get<Need[]>(`${this.URL}/?userId=${userId}`);
  }

}

