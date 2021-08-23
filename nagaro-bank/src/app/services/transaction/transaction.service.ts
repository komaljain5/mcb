import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from 'src/environments/environment';
import { User } from 'src/app/models/user';
import { Transaction } from 'src/app/models/transaction';
import { SortColumn } from 'src/app/components/transaction/transaction-list/sortable.directive';
import { SortDirection } from '@angular/material/sort';


@Injectable({ providedIn: 'root' })
export class TransactionService {
    
    private transactionSubject: BehaviorSubject<Transaction>;
    public transaction:Observable<Transaction>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.transactionSubject = new BehaviorSubject<Transaction>(JSON.parse(localStorage.getItem('transaction')));
        this.transaction=this.transactionSubject.asObservable();
    }

    
    public get transactionValue(): Transaction {
        return this.transactionSubject.value;
    }

    createTransaction(transaction: Transaction) {
        return this.http.post(`${environment.apiUrl}/transaction/create`, transaction);
    }

    getAllTransaction() {
        return this.http.get<Transaction[]>(`${environment.apiUrl}/transaction/all`);
    }

}