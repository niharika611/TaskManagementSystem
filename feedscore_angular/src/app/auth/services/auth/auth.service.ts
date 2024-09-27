import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../storage/storage.service';

const BASIC_URL="http://localhost:8080/";
const BASE_URL="http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {


   }

   createAuthorizationHeader(): HttpHeaders {
    let authHeaders:HttpHeaders=new HttpHeaders();
    //console.log(StorageService.getToken());
    return authHeaders.set(
      'Authorization',
      'Bearer '+StorageService.getToken()
    );
  }

   markNotificationsAsRead(id:number):Observable<any>{
    return this.http.put(BASE_URL+"/api/auth/commentStatus/"+id, {
      headers: this.createAuthorizationHeader()
    });
  }

  getAllCommentStatus():Observable<any>{
    return this.http.get(BASE_URL+"/api/auth/commentStatus", {
      headers: this.createAuthorizationHeader()
    });
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

  reset(resetRequest:any):Observable<any>{
    return this.http.put(BASIC_URL+"api/auth/reset",resetRequest);
  }
}
