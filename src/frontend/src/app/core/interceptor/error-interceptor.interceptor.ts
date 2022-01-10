import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from "rxjs";
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from "../services/authentication-service.service";
import { environment } from "src/environments/environment";
import { ConfigService } from "../services/config.service";

class BackendErrorResponse {
    timestamp: Date;
    status: number;
    error: string;
    path: string;
}

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService, private config: ConfigService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req)
            .pipe(
                catchError((err: HttpErrorResponse & { error?: BackendErrorResponse }) => {
                    if (req.url.startsWith(this.config.getConfig('api_endpoint')) && // Only intercept errors from the API
                        !req.url.endsWith('/authenticate') && // don't intercept authenticate requests
                        !req.url.endsWith('/authenticate/github') && // don't intercept authenticate requests
                        err.status === 403) // API currently returns 403 instead of 401
                    {
                        // auto logout if 401 response returned from api
                        this.authenticationService.logout();
                        location.reload();
                    }

                    const error = err.error?.error || err.statusText;
                    return throwError(error)
                }
            )
        );
    }
}