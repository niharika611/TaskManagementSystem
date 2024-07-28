import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  listOfTasks:any=[];

  constructor(private employeeService: EmployeeService,
    private snackBar: MatSnackBar,
    private router: Router,){
    this.getTasks();
  }


  getTasks() {
    this.employeeService.getEmployeeAllTasksById().subscribe((res)=>{
      this.listOfTasks=res;
    })
  }

  updateStatus(id: number, status: string){
    this.employeeService.updateStatus(id,status).subscribe((res)=>{
      if(res.id!=null){
        this.snackBar.open("Task status updated successfully","Close",{duration: 5000});
        this.getTasks();
      }
      else
      {
        this.snackBar.open("Error while updating task","Close",{duration: 5000}); 
      }

    })
  }

  
}
