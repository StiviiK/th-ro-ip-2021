import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth-guard';
import { AccountComponent } from './pages/account/account.component';
import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { OverviewComponent } from './pages/overview/overview.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },

  { path: "", component: MypapersComponent, canActivate: [AuthGuard] },
  { path: "mypapers", component: MypapersComponent, canActivate: [AuthGuard] },
  { path: "overview", component: OverviewComponent, canActivate: [AuthGuard] },
  { path: "account", component: AccountComponent, canActivate: [AuthGuard] },
  
  { path: "**", redirectTo: "" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
