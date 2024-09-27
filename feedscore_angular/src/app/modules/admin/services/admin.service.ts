import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASE_URL="http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }

  post(postDto: any):Observable<any>{
    return this.http.post(BASE_URL+"/api/admin/post",postDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  getAllPosts():Observable<any>{
    return this.http.get(BASE_URL+"/api/admin/posts", {
      headers: this.createAuthorizationHeader()
    });
  }

  likePost(postDto:any):Observable<any>{
    return this.http.put(BASE_URL+"/api/admin/likePost",postDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  ratePost(postDto:any):Observable<any>{
    return this.http.put(BASE_URL+"/api/admin/ratePost",postDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  deletePost(id:number):Observable<any>{
    return this.http.delete(BASE_URL+"/api/employee/post/"+id, {
      headers: this.createAuthorizationHeader()
    });
  }

  getPostById(id:number):Observable<any>{
    return this.http.get(BASE_URL+"/api/admin/post/"+id, {
      headers: this.createAuthorizationHeader()
    });
  }

  updatePost(id:number,postDto: any):Observable<any>{
    return this.http.put(BASE_URL+`/api/admin/post/${id}`,postDto, {
      headers: this.createAuthorizationHeader()
    });
  }
  getAllPostsByName(name:any):Observable<any>{
    return this.http.get(BASE_URL+`/api/admin/search/${name}`);
  }

  getAllLikesAndRatings():Observable<any>{
    return this.http.get(BASE_URL+"/api/employee/likesRatings", {
      headers: this.createAuthorizationHeader()
    });
  }

  createComment(postId:number, content: string):Observable<any>{
    const payload={
      content:content
    }
    return this.http.post(BASE_URL+"/api/admin/post/comment/"+StorageService.getUserId()+"/"+postId,payload, {
      headers: this.createAuthorizationHeader()
    });
  }

  getCommentsByPostId(id:number):Observable<any>{
    return this.http.get(BASE_URL+"/api/admin/comments/"+id, {
      headers: this.createAuthorizationHeader()
    });
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders:HttpHeaders=new HttpHeaders();
    console.log(StorageService.getToken());
    return authHeaders.set(
      'Authorization',
      'Bearer '+StorageService.getToken()
    );
  }
}
