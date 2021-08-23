import { Injectable } from '@angular/core';
import { User } from '../../models/user';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    window.localStorage.clear();
  }

  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: User): void {
    console.log("user: "+user);
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
    console.log(localStorage.getItem(USER_KEY));
    this.saveToken(user.token);
  }

  public getUser(): any {
    return JSON.parse(localStorage.getItem(USER_KEY));
  }
}
