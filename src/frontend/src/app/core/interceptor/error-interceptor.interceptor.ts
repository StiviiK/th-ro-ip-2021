import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from "rxjs";
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from "../services/authentication-service.service";
import { environment } from "src/environments/environment";

class BackendErrorResponse {
    timestamp: Date;
    status: number;
    error: string;
    path: string;
}

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req)
            .pipe(
                catchError((err: HttpErrorResponse & { error?: BackendErrorResponse }) => {
                    if (req.url.startsWith(environment.apiUrl) && err.status === 401) {
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