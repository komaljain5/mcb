import { Component, OnInit } from '@angular/core';
import { Transaction } from 'src/app/models/transaction';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user : User;

  constructor() { 
    this.user = JSON.parse(localStorage.getItem('auth-user'));
  }

  ngOnInit(): void {
  }

}
