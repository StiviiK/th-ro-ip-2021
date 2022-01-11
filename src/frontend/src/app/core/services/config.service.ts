import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

interface Config {
  api_endpoint: string;
  github: {
    client_id: string;
    callback_url: string;
  }
}

/**
 * Services which provides the configuration for the application
 * 
 * @author Stefan Kürzeder
 */
@Injectable({ providedIn: 'root' })
export class ConfigService {
  private config: Config = null;

  constructor(private http: HttpClient) {

  }

  public getConfig<T extends keyof Config>(key: T) {
    return this.config[key];
  }

  /**
   * Load the configuration from the server
   */
  public load() {
    return new Promise(
      (resolve, reject) => {
        this.http.get('/assets/config.json')
          .pipe(
            catchError((error: any) => {
              reject(error);
              return of(error);
            }),
          )
          .subscribe((responseData) => {
            this.config = responseData;
            resolve(true);
          });
      },
    );
  }
}
