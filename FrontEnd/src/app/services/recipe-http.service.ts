import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecipeHttpService {
  constructor(private http: HttpClient) {}

  getRecipeByUser(userId: string): Observable<unknown> {
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
    return this.http.get(
      'http://localhost:8080/api/orchestrator/recipe/getbyuser',
      options
    );
  }
}
