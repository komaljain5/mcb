import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { SignupRequest } from '../../models/signupRequest';
import { environment } from 'src/environments/environment';
import { LoginRequest } from '../../models/loginRequest';
import { User } from '../../models/user';

const AUTH_API = 'http://localhost:8082/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient) { }

  login(credentials : LoginRequest): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}/api/auth/signin`, credentials);
  }

  register(user): Observable<any> {
    let roles = [];
    if(user.userType === "Admin"){
      roles.push("admin");
    }else {
      roles.push("user");
    }

    new SignupRequest(user.username,user.password, user.email, roles);
    return this.http.post(`${environment.apiUrl}/api/auth/signup`, user);
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('user');
    localStorage.removeItem('transaction');
}

  
}
