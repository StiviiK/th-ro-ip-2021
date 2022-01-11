import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { JwtHelperService } from '../services/jwt-helper.service';
import { AuthenticationService } from '../services/authentication-service.service';

/**
 * AuthGuard, which checks if the user is logged in
 * 
 * @author Stefan Kürzeder
 */
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenicationService: AuthenticationService,
    private jwtHelperService: JwtHelperService,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUser = this.authenicationService.currentUserValue;
    if (currentUser && !this.jwtHelperService.isTokenExpired(currentUser.token)) {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}