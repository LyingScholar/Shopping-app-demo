import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Need } from '../../../../need';
import { LinloutService } from '../../../../services/linlout.service';
import { UserService } from '../../../../services/user.service';
import { NeedService } from '../../../../services/need.service';
import { User } from '../../../../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-need-buton',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-need-buton.component.html',
  styleUrl: './delete-need-buton.component.css'
})
export class DeleteNeedButonComponent {

  @Input() need!: Need;
  user: User = new class {
    id: number = 0;
    name: string = "";
    admin: boolean = false;
  };
  userId: number = 0;
  username: string = '';
  admin: boolean = false;
  loggedIn: boolean = false;


  constructor (private router: Router,private linloutService: LinloutService,private userService: UserService,private needService: NeedService) {
    this.linloutService.user$.subscribe((user: User) => {
      this.user = user;
      this.userId = user.id;
      this.username = user.name;
    })
    this.linloutService.text$.subscribe(text => {
      if(text == 'LOGIN') {
        this.loggedIn = false;
      } else {
        this.loggedIn = true;
      }
    })
    this.linloutService.admin$.subscribe((admin: boolean) => {
      this.admin = admin;
    })
  }

  async delete(): Promise<Need> {
    var myneed = this.needService.deleteNeed(this.need.id);
    this.router.navigate(['/home-page']);
    return myneed;
  }
}
