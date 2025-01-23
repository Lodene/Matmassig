import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from 'src/models/recipe';

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

  addRecipe(recipe: any): Observable<string> {
    const token = localStorage.getItem('auth-user');
    return this.http.post(
      'http://localhost:8080/api/orchestrator/recipe/create', recipe,
      {
        responseType: "text",
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + token,
        }),
      }
    );
  }
}
