import { Component } from '@angular/core';
import { User } from '../../user';
import { LinloutService } from '../../services/linlout.service';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-finalize-button',
  standalone: true,
  imports: [],
  templateUrl: './finalize-button.component.html',
  styleUrl: './finalize-button.component.css'
})
export class FinalizeButtonComponent {
  user: User = new class {
    name: string = '';
    id: number = 0;
    admin: boolean = false;
  }
  userId: number = 0;

  constructor (private linloutService: LinloutService,private userService: UserService,private router: Router) {
    this.linloutService.user$.subscribe(user => {
      this.user = user;
      this.userId = user.id;
    });
  }

  checkout(): void {
    this.userService.callCheckout(this.userId);
    this.router.navigate(['/home-page']);
  }
}
