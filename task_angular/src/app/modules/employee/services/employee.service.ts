import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASE_URL="http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http:HttpClient) { }

  getEmployeeAllTasksById():Observable<any>{
    return this.http.get(BASE_URL+"/api/employee/tasks/"+StorageService.getUserId(), {
      headers: this.createAuthorizationHeader()
    });
    
  }

  updateStatus(id: number, status: string):Observable<any>{
    return this.http.get(BASE_URL+`/api/employee/tasks/${id}/${status}`, {
      headers: this.createAuthorizationHeader()
    });
    
  }


  getTaskById(id:number):Observable<any>{
    return this.http.get(BASE_URL+"/api/employee/task/"+id, {
      headers: this.createAuthorizationHeader()
    });
  }
  
  createComment(taskId:number, content: string):Observable<any>{
    const payload={
      content:content
    }
    return this.http.post(BASE_URL+"/api/employee/task/comment/"+StorageService.getUserId()+"/"+taskId,payload, {
      headers: this.createAuthorizationHeader()
    });
  }

  getComentsByTaskId(id:number):Observable<any>{
    return this.http.get(BASE_URL+"/api/employee/comments/"+id, {
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
