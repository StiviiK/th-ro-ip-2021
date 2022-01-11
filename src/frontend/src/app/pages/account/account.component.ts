import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtHelperService } from 'src/app/core/services/jwt-helper.service';
import { AuthenticationService, User } from 'src/app/core/services/authentication-service.service';

/**
 * Component which displays basic information about the user
 * 
 * @author Stefan Kürzeder
 */
@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
})
export class AccountComponent implements OnInit {

  user: Observable<User>;

  constructor(private authenticationService: AuthenticationService, public jwtHelperService: JwtHelperService) { }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUser;
  }

}
