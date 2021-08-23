import { OnInit, QueryList, ViewChildren } from '@angular/core';
import {Component, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';

import { first } from 'rxjs/operators';
import { Transaction } from 'src/app/models/transaction';
import { TransactionService } from 'src/app/services/transaction/transaction.service';
import { NgbdSortableHeader, SortEvent } from './sortable.directive';

export type SortColumn = keyof Transaction | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: {[key: string]: SortDirection} = { 'asc': 'desc', 'desc': '', '': 'asc' };

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {
  
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  transactions:Transaction[];
 
  collectionSize;

  constructor(private transactionService: TransactionService) {
  }
 
  ngOnInit() {
      this.transactionService.getAllTransaction()
          .pipe(first())
          .subscribe(transactions => {
              this.transactions = transactions;
              this.collectionSize=this.transactions.length;
          }
              );   
  }


}