import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeHttpService {

  constructor(private http: HttpClient) {

  }

  getRecipeByUser(userId: string): Observable<any> {
        const options = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
          })
        };
        return this.http.get('http://localhost:8080/api/orchestrator/recipe/getbyuser/' + userId);
  }

}
