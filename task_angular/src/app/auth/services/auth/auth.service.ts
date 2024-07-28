import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL="http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {


   }

   signup(signUpRequest: any):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    return this.http.post(BASIC_URL+"api/auth/signup",signUpRequest);
  }

  login(loginRequest:any):Observable<any>{
    return this.http.post(BASIC_URL+"api/auth/login",loginRequest);
  }
}
