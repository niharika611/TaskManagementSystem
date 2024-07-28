import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  signUpForm!: FormGroup;
  hidePassword = true;
  hidePassword1 = true;

  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router) {
      this.signUpForm = this.fb.group({
        name: [null, [Validators.required]],
        email: [null, [Validators.required, Validators.email]],
        password: [null, [Validators.required]],
        confirmPassword: [null, [Validators.required]],
      }
      )
  }

  ngOnInit() {
    
  }

  togglePasswordVisibility(){
    this.hidePassword=!this.hidePassword;
  }
  
  togglePasswordVisibility1(){
    this.hidePassword1=!this.hidePassword1;
  }



  onSubmit(){
    console.log(this.signUpForm.value);

    const password=this.signUpForm.get('password')?.value;
    const confirmPassword=this.signUpForm.get('confirmPassword')?.value;

    if(password !== confirmPassword){
      this.snackBar.open('Passwords do not match','close',{duration:5000, panelClass: 'error-snackbar'});
      return;
    }

    this.authService.signup(this.signUpForm.value).subscribe((response) => {
      if(response.id != null){
      this.snackBar.open('Sign up successful!','close',{duration:5000});
      this.router.navigateByUrl("/login");
      }
      else{
        this.snackBar.open('Sign up failed. Please try again','close',{duration:5000, panelClass: 'error-snackbar'});
      }
    },
    );
  }

}
