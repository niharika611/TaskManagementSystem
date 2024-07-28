import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-view-task-details',
  templateUrl: './view-task-details.component.html',
  styleUrl: './view-task-details.component.scss'
})
export class ViewTaskDetailsComponent {

  taskId: number=this.activatedRoute.snapshot.params["id"];
  taskData:any;
  commentForm!: FormGroup;
  comments: any;

  ngOnInit(){
    this.getTaskById();
    this.getCommentsByTaskId();
  }

  constructor(private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,){ 
      this.commentForm = this.fb.group({
      content: [null, [Validators.required]],
    })

  }

  getTaskById(){
    this.adminService.getTaskById(this.taskId).subscribe((res)=>{
      this.taskData=res;
    })
  }

  publishComment(){
    this.adminService.createComment(this.taskId, this.commentForm.get("content")?.value).subscribe((res) =>{
      if(res.id!=null){
        this.snackBar.open("Comment posted updated successfully","Close",{duration: 5000});
        this.getCommentsByTaskId();
      }
      else
      {
        this.snackBar.open("Something went wrong","Close",{duration: 5000}); 
      }
    })
  }

  getCommentsByTaskId(){
    this.adminService.getComentsByTaskId(this.taskId).subscribe((res)=>{
      this.comments=res;
    })
  }


}
