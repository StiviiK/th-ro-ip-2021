import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { GraphviewComponent } from './components/graphview/graphview.component';
import { PaperitemComponent } from './components/paperitem/paperitem.component';
import { AddpapersdialogComponent } from './components/addpapersdialog/addpapersdialog.component';
import { MatRadioModule } from '@angular/material/radio';
import { MatListModule } from '@angular/material/list';

import { LoginComponent } from './components/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './core/interceptor/jwt-interceptor.interceptor';
import { ErrorInterceptor } from './core/interceptor/error-interceptor.interceptor';
import { ConfigService } from './core/services/config.service';
import { MyOverviewComponent } from './pages/my-overview/my-overview/my-overview.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { UsersComponent } from './pages/users/users.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MypapersComponent,
    GraphviewComponent,
    PaperitemComponent,
    AddpapersdialogComponent,
    LoginComponent,
    AddpapersdialogComponent,
    MyOverviewComponent,
    UsersComponent,
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
    MatButtonModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    FormsModule,
    HttpClientModule,
    MatListModule,
    MatTooltipModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {
      provide: APP_INITIALIZER,
      useFactory: (config: ConfigService) => () => config.load(),
      deps: [ConfigService],
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [AddpapersdialogComponent],
})
export class AppModule {}
