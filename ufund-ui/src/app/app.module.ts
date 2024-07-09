import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component'
import { AppRoutingModule } from './app-routing.module';
import { HomePageModule } from './home-page.module';
import { LoginPageModule } from './login-page.module';
import { LoginPageComponent } from './login-page/login-page.component';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HomePageModule,
    LoginPageModule
  ],
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }