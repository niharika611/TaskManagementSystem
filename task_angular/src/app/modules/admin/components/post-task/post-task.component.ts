import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-post-task',
  templateUrl: './post-task.component.html',
  styleUrl: './post-task.component.scss'
})
export class PostTaskComponent {

  taskForm!: FormGroup;
  listOfEmployees:any=[];
  listOfPriorities:any=["LOW","MEDIUM","HIGH"];

  constructor(private adminService: AdminService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router){
    this.taskForm = this.fb.group({
      employeeId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      dueDate: [null, [Validators.required]],
      priority: [null, [Validators.required]],
    })
    this.getUsers();
  }

  getUsers(){
    this.adminService.getUsers().subscribe((res)=>{
      this.listOfEmployees=res;
      console.log(res);
    });
  }

  postTask(){
    this.adminService.postTask(this.taskForm.value).subscribe((res)=>{
      if(res.id!=null){
        this.snackBar.open("Task posted successfully","Close",{duration:5000});
        this.router.navigateByUrl("/admin/dashboard");
      } else{
        this.snackBar.open("Something went wrong","ERROR",{duration:5000});
      }
    })
  }
}
