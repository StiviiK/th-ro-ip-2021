import { Injectable } from "@angular/core";
import { JwtHelperService as _JwtHelperService } from "@auth0/angular-jwt";

@Injectable({ providedIn: 'root' })
export class JwtHelperService extends _JwtHelperService {
    constructor() {
        super();
    }
}