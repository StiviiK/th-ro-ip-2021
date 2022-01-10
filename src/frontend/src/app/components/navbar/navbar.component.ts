import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication-service.service';

/**
 * Navbar at the top of the screen.
 * @author Lukas Metzner
 * @author Stefan Kürzeder
 * @author Alessandro Sorro
 */
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(public authenticationService: AuthenticationService) { }

  logout() {
    this.authenticationService.logout();
    location.reload();
  }
}
