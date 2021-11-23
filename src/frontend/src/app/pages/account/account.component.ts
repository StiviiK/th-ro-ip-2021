import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService, User } from 'src/app/core/services/authentication-service.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  user: Observable<User>;

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUser;
  }

  parseJwt(token: string) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return window.atob(base64);
  }

}
