import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { MypapersComponent } from './mypapers/mypapers.component';
import { OverviewComponent } from './overview/overview.component';

const routes: Routes = [
  { path: "", component: MypapersComponent },
  { path: "mypapers", component: MypapersComponent },
  { path: "overview", component: OverviewComponent },
  { path: "account", component: AccountComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
