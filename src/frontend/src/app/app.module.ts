import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { OverviewComponent } from './pages/overview/overview.component';
import { AccountComponent } from './pages/account/account.component';
import { GraphviewComponent } from './components/graphview/graphview.component';
import { PaperitemComponent } from './components/paperitem/paperitem.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptor/jwt-interceptor.interceptor';
import { ErrorInterceptor } from './interceptor/error-interceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MypapersComponent,
    OverviewComponent,
    AccountComponent,
    GraphviewComponent,
    PaperitemComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxGraphModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
