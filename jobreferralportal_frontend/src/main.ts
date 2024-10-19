import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component'; 
import { importProvidersFrom } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms'; 
import { AppRoutingModule } from './app/app.routes'; 
import {  provideHttpClient } from '@angular/common/http'; 


bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      ReactiveFormsModule, 
      AppRoutingModule, 
    ),
    provideHttpClient() 
  ]
})
.catch(err => console.error(err));
