import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './auth/services/storage/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'task_angular';

  isEmployeeLoggedIn: boolean = StorageService.isEmployeeLoggedIn();
  isAdminLoggedIn: boolean = StorageService.isAdminLoggedIn();

  constructor(private router : Router){

  }

  ngOnInit(): void{
    this.router.events.subscribe(event => {
      this.isEmployeeLoggedIn = StorageService.isEmployeeLoggedIn();
      this.isAdminLoggedIn=StorageService.isAdminLoggedIn();
    })
  }

  logOut(){
    StorageService.signOut();
    this.router.navigateByUrl('/login');
  }
}
