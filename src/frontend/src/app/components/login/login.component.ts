import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/core/services/authentication-service.service';
import { ConfigService } from 'src/app/core/services/config.service';

/**
 * Login component, which handles all types of login
 * 
 * @author Stefan Kürzeder
 */
@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';

    githubUrl: string;
    githubLoading: boolean = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private config: ConfigService
    ) { }

    /**
     * Initialize the component
     * Initiates the GitHub login process if the auth code is present
     */
    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

        // reset login status
        this.authenticationService.logout();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

        // update the github redirect url
        var githubConfig = this.config.getConfig('github');
        this.githubUrl = "https://github.com/login/oauth/authorize?client_id=" + githubConfig.client_id + "&scope=user&redirect_uri=" + githubConfig.callback_url + "?returnUrl=" + this.returnUrl;

        // reset error message on each login attempt
        this.loginForm.valueChanges.subscribe(_ => {
            this.error = '';
        });

        // check if we have a github code in the url
        if (this.route.snapshot.queryParams['code']) {
            this.githubLoading = true; // display loading message while we wait for the authentication to complete
            this.authenticationService.loginWithGithub(this.route.snapshot.queryParams['code'])
                .subscribe(
                    _ => {
                        this.router.navigate([this.returnUrl]);
                    },
                    error => {
                        this.error = error;
                        this.loading = false;
                    });
        }
    }

    // convenience getter for easy access to form fields
    get f() { 
        return this.loginForm.controls; 
    }

    /**
     * Initiates the login process
     * @returns true if the form is valid
     */
    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            this.error = 'Please enter a valid username and password';
            this.loading = false;
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                _ => {
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }
}
