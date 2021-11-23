import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtHelperService } from 'src/app/core/helper/jwt.helper';
import { AuthenticationService, User } from 'src/app/core/services/authentication-service.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  user: Observable<User>;

  constructor(private authenticationService: AuthenticationService, public jwtHelperService: JwtHelperService) { }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUser;
  }

}
