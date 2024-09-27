import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UpdatePostComponent } from './components/update-post/update-post.component';

const routes: Routes = [
  {path:"dashboard", component: DashboardComponent},
  {path:"post", component: CreatePostComponent},
  {path:"post/:id/edit", component: UpdatePostComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
