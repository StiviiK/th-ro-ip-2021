import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './core/guards/auth-guard';
import { MyOverviewComponent } from './pages/my-overview/my-overview/my-overview.component';
import { MypapersComponent } from './pages/mypapers/mypapers.component';
import { UsersComponent } from './pages/users/users.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'mypapers', pathMatch: 'full' },
  {
    path: '',
    children: [
      { path: 'mypapers', component: MypapersComponent },
      { path: 'my-overview', component: MyOverviewComponent },
      { path: 'users', component: UsersComponent },
      { path: '**', redirectTo: 'mypapers' },
    ],
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
