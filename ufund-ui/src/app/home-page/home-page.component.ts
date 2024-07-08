import { Component } from '@angular/core';

import {Router, ActivatedRoute, ParamMap } from '@angular/router';
import {switchMap } from 'rxjs/operators';


@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  title = 'U-Fund Home Page';
}
