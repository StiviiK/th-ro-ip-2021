import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({ providedIn: 'root' })
export class LocalJwtHelperService extends JwtHelperService {
    constructor() {
        super();
    }
}