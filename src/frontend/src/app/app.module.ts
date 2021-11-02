import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MypapersComponent,
    OverviewComponent,
    AccountComponent,
    GraphviewComponent,
    PaperitemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxGraphModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
