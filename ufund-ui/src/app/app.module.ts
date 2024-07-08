import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

//import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component'
import {RouterModule, Routes} from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { HomePageModule } from './home-page.module';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HomePageModule
  ],
  declarations: [
    AppComponent,
    HomePageComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }