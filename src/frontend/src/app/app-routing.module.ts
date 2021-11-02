import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountComponent } from './pages/account/account.component';
import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { OverviewComponent } from './pages/overview/overview.component';

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
