import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiscoveryHttpService {

  constructor(private http: HttpClient) { }


  discoverRecipes(discoveryRequest: any): Observable<any> {
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
    return this.http.post('urlToDo', discoveryRequest, options);
  }
}
