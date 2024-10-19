
import { NgModule } from '@angular/core';
import { LoginComponent } from './Core/login/login.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app.routes';
import { HeaderComponent } from './Shared/header/header.component';

@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
  ],
  bootstrap: []
})
export class AppConfig { }
