import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrl: './update-post.component.scss'
})
export class UpdatePostComponent {

  id:number=this.route.snapshot.params["id"];

  taskForm!: FormGroup;
  selectedFile!: File;
  imagePreview!: any;
  existingImage!: string | null;

  constructor(private employeeService: EmployeeService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router){
      this.getPostById();
    this.taskForm = this.fb.group({
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
    })
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.existingImage=null;
  }

  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  getPostById(){
    this.employeeService.getPostById(this.id).subscribe((res:any)=>{
      console.log(res);
      this.taskForm.patchValue(res);
      this.existingImage='data:image/jpeg;base64,'+res.image;
    })
  }

  updatePost(){
    const formData = {
      ...this.taskForm.value,
      userId: StorageService.getUserId(),
      //'image': this.imagePreview?.split(',')[1] 
      'image': this.selectedFile ? this.imagePreview?.split(',')[1] : this.existingImage?.split(',')[1] // Use imagePreview if new file selected, else existingImage
    };

    this.employeeService.updatePost(this.id,formData).subscribe((res:any)=>{
      console.log(formData);
      if(res.id!=null){
        this.snackBar.open("Post updated successfully","Close",{duration:5000});
        this.router.navigateByUrl("/employee/dashboard");
      } else{
        this.snackBar.open("Something went wrong","ERROR",{duration:5000});
      }
    })
  }
}
