import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ingredient } from 'src/models/ingredient';

@Injectable({
  providedIn: 'root'
})
export class InventoryHttpService {

  constructor(private http: HttpClient) {

  }
  getUserItem():Observable<any> {
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
      return this.http.get("http://localhost:8080/api/orchestrator/inventory/getbyuser", options);
  }
  

  postUserItem(item: Ingredient): Observable<any> {
    item.userId = -1;
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
      return this.http.post("http://localhost:8080/api/orchestrator/inventory/create", item, options);
    }
}
