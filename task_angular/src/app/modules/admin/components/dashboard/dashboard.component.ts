import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  listOfTasks: any=[];
  searchForm!: FormGroup;

  constructor(private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder,){
    this.searchForm = this.fb.group({
      title: [null],
    });
    this.getTasks();
  }

  getTasks(){
    this.adminService.getAllTasks().subscribe((res)=>{
      console.log(res);
      this.listOfTasks=res;
    })
  }

  deleteTask(id:number){
    this.adminService.deleteTask(id).subscribe((res)=>{
      this.snackBar.open("Task deleted successfully","Close",{duration:5000});
      this.listOfTasks=this.getTasks();
    })
  }

  searchTask(){
    this.listOfTasks=[];
    const title=this.searchForm.get('title')!.value;
    console.log(title);
    this.adminService.searchTask(title).subscribe((res)=>{
      console.log(res);
      this.listOfTasks=res;
    })
  }
}
