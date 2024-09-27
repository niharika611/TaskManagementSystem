import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth/services/auth/auth.service';
import { StorageService } from './auth/services/storage/storage.service';
import { EmployeeService } from './modules/employee/services/employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class AppComponent {
  title = 'task_angular';
  notifications: any[] = [];
  loggedInUserId:any;

  isEmployeeLoggedIn: boolean = StorageService.isEmployeeLoggedIn();
  isAdminLoggedIn: boolean = StorageService.isAdminLoggedIn();

  constructor(private router : Router,
    private cdr: ChangeDetectorRef,
    private authService: AuthService){
      
  }

  toggleForm() {
    //this.showForm = !this.showForm;
    if(this.loggedInUserId==1)
    this.router.navigateByUrl('/admin/post');
    else
    this.router.navigateByUrl('/employee/post');
  }

  ngOnInit(): void{
    this.router.events.subscribe(event => {
      this.isEmployeeLoggedIn = StorageService.isEmployeeLoggedIn();
      this.isAdminLoggedIn=StorageService.isAdminLoggedIn();
      this.loggedInUserId=StorageService.getUserId();
      if (this.loggedInUserId) {
        this.getCommentStatus();
      }
    });
  }

  logOut(){
    StorageService.signOut();
    this.router.navigateByUrl('/login');
  }

  getCommentStatus(){
    const loggedInUserId = StorageService.getUserId();
    this.authService.getAllCommentStatus().subscribe((res: any) => {
      this.notifications = res
      .filter((comment: any) => 
        comment.postCreatedUserId === loggedInUserId && 
        comment.userId !== loggedInUserId && 
        comment.readStatus === false
      )
      .map((comment: any) => ({
        message: `${comment.commentedUserName} commented on your post`, 
        postId: comment.postId,
        commentId: comment.id, 
        readStatus: comment.readStatus,
      }));
    //  console.log('Filtered Notifications:', this.notifications);
    });
    this.cdr.detectChanges();
  }

  showNotifications = false;

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
    // console.log(this.showNotifications);
    // console.log(this.notifications.length);
    if (this.showNotifications) {
      this.getCommentStatus();
    } else {
      this.authService.markNotificationsAsRead(this.loggedInUserId).subscribe((res) => {
       // console.log(res);
        this.notifications = []; 
      });
    }
  }
  
  openNotifications() {
    
    //console.log('Notifications clicked');
  }
}
