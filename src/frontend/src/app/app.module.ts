import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MypapersComponent } from './mypapers/mypapers.component';
import { OverviewComponent } from './overview/overview.component';
import { AccountComponent } from './account/account.component';
import { GraphviewComponent } from './graphview/graphview.component';
import { PaperitemComponent } from './paperitem/paperitem.component';

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
