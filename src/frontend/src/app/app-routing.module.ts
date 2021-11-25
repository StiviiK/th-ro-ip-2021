import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './core/guards/auth-guard';
import { AccountComponent } from './pages/account/account.component';
import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { OverviewComponent } from './pages/overview/overview.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "", redirectTo: "mypapers", pathMatch: "full" },
  {
    path: "",
    children: [
      { path: "mypapers", component: MypapersComponent },
      { path: "overview", component: OverviewComponent },
      { path: "account", component: AccountComponent },
      { path: "**", redirectTo: "mypapers" }
    ],
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
