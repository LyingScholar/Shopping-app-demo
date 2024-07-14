import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
//import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';

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

}

