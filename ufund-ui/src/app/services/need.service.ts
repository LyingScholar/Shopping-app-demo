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

  editing: number = 0;

  constructor(private http: HttpClient) { }

  public setEdit (id: number): void {
    this.editing = id;
  }

  public getEdit (): number {
    return this.editing;
  }

  async getNextId (): Promise<number> {
    var tempNeeds: Need[] = await this.callGetNeeds();
    return tempNeeds[tempNeeds.length-1].id+1;
  }

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
    var needs: Need[] = await firstValueFrom(
      this.http.get<Need[]>(`${this.apiUrl}/Needs/?name=${text}`));
      this.searchSubject = new BehaviorSubject<Need[]>(needs);
      this.search$ = this.searchSubject.asObservable();
  }

  async deleteNeed(id: number): Promise<Need> {
    var need: Need = await firstValueFrom(
      this.http.delete<Need>(`${this.apiUrl}/Needs/${id}`));
      return need;
  }

  async makeNeed(need: Need): Promise<void> {
    await firstValueFrom(this.http.post<void>(`${this.apiUrl}/Needs${need}`,{observable: "response"}));
  }

  async editNeed(need: Need): Promise<void> {
    await firstValueFrom(this.http.put<void>(`${this.apiUrl}/Needs${need}`,{observable: "response"}));
  }
}
