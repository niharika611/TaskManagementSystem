import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm!: FormGroup;
  hidePassword = true;

  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router) {
      this.loginForm = this.fb.group({
        email: [null, [Validators.required]],
        password: [null, [Validators.required]],
      }
      )
  }

  ngOnInit() {
   
  }

  togglePasswordVisibility(){
    this.hidePassword=!this.hidePassword;
  }

  onSubmit(){
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm.value).subscribe((res) => {
      console.log(res);
      if(res.userId!=null){
        const user = {
          id: res.userId,
          role: res.userRole,
        }
        console.log(user);
          console.log(res.jwt);
        StorageService.saveUser(user);
        StorageService.saveToken(res.jwt);
        
        if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("/admin/dashboard");
        }
        else if(StorageService.isEmployeeLoggedIn()){
          this.router.navigateByUrl("/employee/dashboard");
          this.snackBar.open('Login successful!','close',{duration:5000});
        }
      }
      else{
        this.snackBar.open('Invalid credentials','close',{duration:5000, panelClass: 'error-snackbar'});
      }
    },
    );
  }

}
