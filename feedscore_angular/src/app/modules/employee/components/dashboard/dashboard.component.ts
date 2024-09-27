import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  showForm: boolean = false;
  listOfPosts: any=[];
  loggedInUserId:any;
  searchProductForm!: FormGroup;
  taskData:any;
  commentForm!: FormGroup;
  comments: any=[];
  notifications: any[] = [];

  constructor(private employeeService: EmployeeService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,)
  {
    this.getPosts();
    this.getLikesAndRatings();
    this.loggedInUserId=StorageService.getUserId();
    // this.getCommentStatus();
  }

  // getLikesAndRatings() {
  //   this.loggedInUserId=StorageService.getUserId();
  //   console.log(this.loggedInUserId);
  //   this.employeeService.getAllLikesAndRatings().subscribe((res:any)=>{
  //     this.listOfPosts.forEach((post: any) => {
  //       const likesRatingsEntry = res.find((entry: any) =>
  //       entry.postId === post.id && entry.userId === this.loggedInUserId);
  //       if (likesRatingsEntry) {
  //         post.liked = likesRatingsEntry.liked;
  //         post.rating = likesRatingsEntry.rating;
  //       }
  //     });
  //     console.log(this.listOfPosts);
  //   });
  // }

  // getCommentStatus(){
  //   const loggedInUserId = StorageService.getUserId();
  //   this.employeeService.getAllCommentStatus().subscribe((res: any[]) => {
  //     this.notifications = res
  //     .filter((comment: any) => 
  //       comment.postCreatedUserId === loggedInUserId && 
  //       comment.userId !== loggedInUserId && 
  //       comment.readStatus === false
  //     )
  //     .map((comment: any) => ({
  //       message: `${comment.commentedUserName} commented on your post`, 
  //       postId: comment.postId,
  //       commentId: comment.id, 
  //       readStatus: comment.readStatus,
  //     }));
  //     console.log('Filtered Notifications:', this.notifications);
  //   });
  // }

//   showNotifications = false;

// toggleNotifications() {
//   this.showNotifications = !this.showNotifications;
//   console.log(this.showNotifications);
//   console.log(this.notifications.length);
//   if (this.showNotifications) {
//     this.getCommentStatus();
//   } else {
//     this.employeeService.markNotificationsAsRead(this.loggedInUserId).subscribe(() => {
//       this.notifications = []; 
//     });
//   }
// }



  getLikesAndRatings() {
    this.loggedInUserId = StorageService.getUserId();
    console.log(this.loggedInUserId);
  
    this.employeeService.getAllPosts().subscribe((posts: any) => {
      this.listOfPosts = posts;
  
      this.employeeService.getAllLikesAndRatings().subscribe((res: any) => {
        this.listOfPosts.forEach((post: any) => {
          post.image = 'data:image/jpeg;base64,' + post.image;

          const likesRatingsEntry = res.find((entry: any) =>
            entry.postId === post.id && entry.userId === this.loggedInUserId);
          
          if (likesRatingsEntry) {
            post.liked = likesRatingsEntry.liked;
            post.rating = likesRatingsEntry.rating;
          }
  
          const likesForPost = res.filter((entry: any) => 
          entry.postId === post.id && entry.liked === true
        );
        post.likeCount = likesForPost.length; 

          const ratingsForPost = res.filter((entry: any) => 
            entry.postId === post.id && entry.rating > 0
          );
  
          post.ratingCount = ratingsForPost.length; 
          if (post.ratingCount > 0) {
            const sum = ratingsForPost.reduce((acc: any, entry: { rating: any; }) => acc + entry.rating, 0);
            post.avgRating = parseFloat((sum / post.ratingCount).toFixed(1)); 
          } else {
            post.avgRating = 0; 
          }
        });
        
        console.log(this.listOfPosts);
      });
    });
  }
  
  
  
  toggleForm() {
    this.showForm = !this.showForm;
    this.router.navigateByUrl('/employee/post');
  }

  ngOnInit(){
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]],
    }
    );
    this.commentForm = this.fb.group({
      content: [' ', [Validators.required]],
    });
    //this.getPostById();
    //this.getCommentsByPostId();
  }

  getPosts(){
    this.employeeService.getAllPosts().subscribe((res:any)=>{
      this.listOfPosts = []; 
      res.forEach(((element: { processedImg: string; image: string; }) => {
        element.image = 'data:image/jpeg;base64,'+element.image;
        this.listOfPosts.push(element);
      }))
    })
console.log(this.listOfPosts);
   
  }

  likePost(post:any){
    if (post.likeCount === undefined || post.likeCount === null) {
      post.likeCount = 0;
    }
    post.liked = !post.liked;
    const payload = {
      id: post.id,
      userId: StorageService.getUserId(),
      liked: post.liked,
     };
    this.employeeService.likePost(payload).subscribe((res:any)=>{
      console.log(res);
      //this.getPosts();
      if (post.liked) {
        post.likeCount++;
      } else {
        post.likeCount--;  
      }
    });
    console.log(post.liked);
  }

  ratePost(post: any, rating: number) {
    post.rating = rating;
    const payload = {
      id: post.id,
      rating: rating,
      userId: StorageService.getUserId(),
    };
  
    this.employeeService.ratePost(payload).subscribe((res: any) => {
      console.log(res);
    });
    this.employeeService.getAllLikesAndRatings().subscribe((res: any) => {
      const likesRatingsEntry = res.find((entry: any) =>
        entry.postId === post.id && entry.userId === StorageService.getUserId()
      );
      const previousRating = likesRatingsEntry ? likesRatingsEntry.rating : 0;
  
      if (!post.ratingCount) {
        post.ratingCount = 0;
      }
      if (previousRating === 0) {
        post.ratingCount += 1;
      }
      const totalRating = (post.avgRating * post.ratingCount) - previousRating + rating;
  
      post.avgRating = parseFloat((totalRating / post.ratingCount).toFixed(1));
    });
  }
  

  deletePost(id:number){
    this.employeeService.deletePost(id).subscribe((res:any)=>{
      this.snackBar.open("Post deleted successfully","Close",{duration:5000});
      this.listOfPosts=this.getPosts();
    })
    //this.listOfPosts=this.getPosts();
  }

  submitForm(){
    this.listOfPosts=[];
    const title=this.searchProductForm.get('title')!.value;
    console.log(title);
    this.employeeService.getAllPostsByName(title).subscribe((res)=>{
      console.log(res);
      
      res.forEach(((element: { processedImg: string; image: string; }) => {
        element.image = 'data:image/jpeg;base64,'+element.image;
        this.listOfPosts.push(element);
      }))
    })
  }

  // getPostById(){
  //   this.employeeService.getPostById(this.postId).subscribe((res)=>{
  //     this.taskData=res;
  //   })
  // }

  publishComment(postId:number){
    this.employeeService.createComment(postId, this.commentForm.get("content")?.value).subscribe((res) =>{
      if(res.id!=null){
        this.snackBar.open("Comment posted updated successfully","Close",{duration: 5000});
        this.getCommentsByPostId(postId);
        //this.commentForm.reset();
        this.commentForm.get('content')!.setValue(' ');

        // const newNotification = {
        //   message: `New comment on post ${postId}`,
        //   timestamp: new Date(),
        // };
        // this.notifications.push(newNotification); 
        // console.log(this.employeeService.notifications);
      }
      else
      {
        this.snackBar.open("Something went wrong","Close",{duration: 5000}); 
      }
    })
  }

  getCommentsByPostId(postId:number){
    this.employeeService.getCommentsByPostId(postId).subscribe((res)=>{
      this.comments=res;
      console.log(this.comments);
    })
  }

  isCommentVisible = false;
  activeCommentPostId: number | null = null;


  commentOnPost(post: any) {
    if (this.activeCommentPostId === post.id) {
      this.activeCommentPostId = null; 
    } else {
      this.activeCommentPostId = post.id; 
    }
    this.getCommentsByPostId(post.id);
  }
  
    

 
}

