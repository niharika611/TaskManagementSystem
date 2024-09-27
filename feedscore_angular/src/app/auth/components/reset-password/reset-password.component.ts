import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent {
  resetForm!: FormGroup;
  hidePassword = true;
  hidePassword1 = true;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router) {
      this.resetForm = this.fb.group({
        password: [null, [Validators.required]],
        confirmPassword: [null, [Validators.required]],
        email: [null, [Validators.required, Validators.email]],
      }
      )
  }

  ngOnInit() {
   
  }

  togglePasswordVisibility1(){
    this.hidePassword1=!this.hidePassword1;
  }

  togglePasswordVisibility(){
    this.hidePassword=!this.hidePassword;
  }

  onSubmit(){
    this.authService.reset(this.resetForm.value).subscribe((res) => {
      this.router.navigateByUrl("/login");
      this.snackBar.open('Password reset successful!','Close',{duration:5000});
    })
  }
}
