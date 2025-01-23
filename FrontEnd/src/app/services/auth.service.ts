import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(data: UserData): Observable<boolean> {
    let subject = new Subject<boolean>();
    this.http.post('http://localhost:8080/auth/login', data).subscribe({
      next: (result: any) => {
        if (!!result) {
          localStorage.setItem("auth-user", result.token);
          subject.next(true);
        }
        else {
          subject.next(false);
        }
      },
      error: () => {
        subject.next(false);
      }
    });
    return subject.asObservable();
  }

  signUp(data: UserData): Observable<boolean> {
    let subject = new Subject<boolean>();
    this.http.post('http://localhost:8080/auth/signup', data, {responseType: 'text'}).subscribe((result: any) => {
      if (!!result) {
        subject.next(true);
      } else {
        subject.next(false);
      }
    });
    return subject.asObservable();
  }

  logout() {
    localStorage.removeItem("auth-user")
  }

  isAuthenticated(): string | null {
    return localStorage.getItem("auth-user");
  }
}

export interface UserData {
  name?: string;
  email: string;
  password: string;
}