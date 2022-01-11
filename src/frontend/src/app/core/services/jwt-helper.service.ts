import { Injectable } from '@angular/core';
import { JwtHelperService as _JwtHelperService } from '@auth0/angular-jwt';

/**
 * Injectable wrapper around the JWTHelperService from the @auth0/angular-jwt library.
 * 
 * @author Stefan Kürzeder
 */
@Injectable({ providedIn: 'root' })
export class JwtHelperService extends _JwtHelperService {     
  // do not remove consturctor
  public constructor(){
    super();
  }
}