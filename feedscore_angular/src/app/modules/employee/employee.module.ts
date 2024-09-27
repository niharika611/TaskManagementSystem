import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DemoAngularMaterialModule } from '../../DemoAngularMaterialModule';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { UpdatePostComponent } from './components/update-post/update-post.component';

@NgModule({
  declarations: [
    DashboardComponent,
    CreatePostComponent,
    UpdatePostComponent,
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    DemoAngularMaterialModule
  ]
})
export class EmployeeModule { }
