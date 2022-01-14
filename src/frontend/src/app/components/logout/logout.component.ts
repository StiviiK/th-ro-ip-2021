import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication-service.service';

/**
 * Logout component
 * 
 * @author Stefan Kürzeder
 */
@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.logout();
    location.reload();
  }

}
