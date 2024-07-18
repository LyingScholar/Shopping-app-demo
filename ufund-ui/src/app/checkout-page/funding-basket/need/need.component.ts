import { Component, Input } from '@angular/core';
import { Need } from '../../../needs-page/needs-list/need';

@Component({
  selector: 'app-need',
  standalone: true,
  imports: [],
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {
  @Input() need!: Need;
}
