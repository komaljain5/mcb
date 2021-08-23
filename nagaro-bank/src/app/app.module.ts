import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/user/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/user/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { authInterceptorProviders } from './interceptors/auth.interceptor';
import { CustomerFormComponent } from './components/transaction/customer-form/customer-form.component';
import { TransactionListComponent } from './components/transaction/transaction-list/transaction-list.component';
import { ViewTransactionComponent } from './components/transaction/view-transaction/view-transaction.component';
import { NavbarComponent } from './components/common/navbar/navbar.component';
import { backendApiProvider } from './interceptors/transaction-api-mock.interceptor';
import { ProfileComponent } from './components/user/profile/profile.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    CustomerFormComponent,
    TransactionListComponent,
    ViewTransactionComponent,
    NavbarComponent,
    ProfileComponent
    ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders, backendApiProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
