import { Routes } from '@angular/router';
import { HomePageComponent } from '../home-page/home-page.component';
import { LoginPageComponent } from '../login-page/login-page.component';
import { NeedsPageComponent } from '../needs-page/needs-page.component';
import { CheckoutPageComponent } from '../checkout-page/checkout-page.component';
import { EditNeedPageComponent } from '../edit-need-page/edit-need-page.component';
import { AddNeedPageComponent } from '../add-need-page/add-need-page.component';

export const routes: Routes = [
  {path:'', redirectTo: '/home-page', pathMatch: 'full'},
  {path: 'home-page', component: HomePageComponent},
  {path: 'login-page', component: LoginPageComponent},
  {path: 'needs-page', component: NeedsPageComponent},
  {path: 'checkout-page', component: CheckoutPageComponent},
  {path: 'edit-need-page', component: EditNeedPageComponent},
  {path: 'add-need-page', component: AddNeedPageComponent}
]
