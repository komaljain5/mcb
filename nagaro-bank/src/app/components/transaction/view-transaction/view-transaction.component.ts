import { Component, OnInit } from '@angular/core';
import { Transaction } from 'src/app/models/transaction';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-view-transaction',
  templateUrl: './view-transaction.component.html',
  styleUrls: ['./view-transaction.component.scss']
})
export class ViewTransactionComponent implements OnInit {

  transaction : Transaction;
  constructor() { 
  this.transaction = JSON.parse(localStorage.getItem('transaction'));
  }

  ngOnInit(): void {
  }

}
