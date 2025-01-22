import { Injectable } from '@angular/core';
import { UserData } from '../home/home.page';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  login(data: UserData) {
    localStorage.setItem("auth-user", data.email);
  }

  logout() {
    localStorage.removeItem("auth-user")
  }

  isAuthenticated(): string | null {
    return localStorage.getItem("auth-user");
  }
}
