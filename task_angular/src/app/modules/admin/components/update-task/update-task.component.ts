import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrl: './update-task.component.scss'
})
export class UpdateTaskComponent {

  id:number=this.route.snapshot.params["id"];

  updateTaskForm!: FormGroup;
  listOfEmployees:any=[];
  listOfPriorities:any=["LOW","MEDIUM","HIGH"];
  listOfTaskStatus:any=["PENDING", "INPROGRESS", "COMPLETED", "DEFERRED", "CANCELLED"];


  constructor(private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,){
    this.getTaskById();
    this.getUsers();
    this.updateTaskForm = this.fb.group({
      employeeId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      dueDate: [null, [Validators.required]],
      priority: [null, [Validators.required]],
      taskStatus: [null, [Validators.required]],
    })
  }

  getTaskById(){
    this.adminService.getTaskById(this.id).subscribe((res)=>{
      this.updateTaskForm.patchValue(res);
    })
  }

  getUsers(){
    this.adminService.getUsers().subscribe((res)=>{
      this.listOfEmployees=res;
    });
  }

  updateTask(){
    this.adminService.updateTask(this.id,this.updateTaskForm.value).subscribe((res)=>{
      console.log(this.updateTaskForm.value);
      if(res.id!=null){
        this.snackBar.open("Task updated successfully","Close",{duration:5000});
        this.router.navigateByUrl("/admin/dashboard");
      } else{
        this.snackBar.open("Something went wrong","ERROR",{duration:5000});
      }
    })
  }

}
