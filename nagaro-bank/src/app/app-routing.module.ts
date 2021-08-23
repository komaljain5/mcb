import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/user/login/login.component';
import { RegisterComponent } from './components/user/register/register.component';
import { CustomerFormComponent } from './components/transaction/customer-form/customer-form.component';
import { TransactionListComponent } from './components/transaction/transaction-list/transaction-list.component';
import { ViewTransactionComponent } from './components/transaction/view-transaction/view-transaction.component';
import { ProfileComponent } from './components/user/profile/profile.component';

const routes: Routes = [
  { path: '', component: HomeComponent,canActivate:[AuthGuard]},
    { path: 'profile', component: ProfileComponent,canActivate:[AuthGuard] },
    { path: 'customerForm', component: CustomerFormComponent,canActivate:[AuthGuard] },
    { path: 'transactions', component: TransactionListComponent,canActivate:[AuthGuard] },
    { path: 'viewTransaction', component: ViewTransactionComponent,canActivate:[AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 
  
}
