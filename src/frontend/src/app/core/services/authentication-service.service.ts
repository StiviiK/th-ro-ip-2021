import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConfigService } from './config.service';

export class User {
  id: string;
  username: string;
  token?: string;
}

/**
 * Service which helps to manage the authentication
 * 
 * @author Stefan Kürzeder
 */
@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private config: ConfigService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  private authenticationHandler(user: User): User {
    // login successful if there's a jwt token in the response
    if (user && user.token) {
      // store user details and jwt token in local storage to keep user logged in between page refreshes
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
    }

    return user;
  }

  /**
     * Login in the user with the given username and password
     * @param username Username
     * @param password Password
     * @returns User if successful, otherwise null
     */
  login(username: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.config.getConfig('api_endpoint')}/authenticate`, { username, password })
      .pipe(
        map(this.authenticationHandler.bind(this)),
      );
  }

  /**
     * Login in the user with the given auth code
     * @param code github auth code
     * @returns User if successful, otherwise null
     */
  loginWithGithub(code: string): Observable<User> {
    return this.http.post<User>(`${this.config.getConfig('api_endpoint')}/authenticate/github`, { code })
      .pipe(
        map(this.authenticationHandler.bind(this)),
      );
  }

  /**
     * Destroy the active user session
     */
  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}