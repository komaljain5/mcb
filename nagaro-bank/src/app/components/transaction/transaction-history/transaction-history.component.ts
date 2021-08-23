// import { Component, OnInit } from '@angular/core';
// import { first } from 'rxjs/operators';
// import { Transaction } from 'src/app/models/transaction';
// import { TransactionService } from 'src/app/services/transaction/transaction.service';
// import { TableModule } from 'primeng/table';

// @Component({
//   selector: 'app-transaction-history',
//   templateUrl: './transaction-history.component.html',
//   styleUrls: ['./transaction-history.component.scss']
// })
// export class TransactionHistoryComponent implements OnInit {

//   transactions:Transaction[];
//   first = 0;
//   rows = 10;

//   constructor(private transactionService: TransactionService) {
//   }
 
//   ngOnInit() {
//       this.transactionService.getAllTransaction()
//           .pipe(first())
//           .subscribe(transactions => {
//               this.transactions = transactions;
//           }
//               );   
//   }

//   next() {
//     this.first = this.first + this.rows;
// }

// prev() {
//     this.first = this.first - this.rows;
// }

// reset() {
//     this.first = 0;
// }

// isLastPage(): boolean {
//     return this.transactions ? this.first === (this.transactions.length - this.rows): true;
// }

// isFirstPage(): boolean {
//     return this.transactions ? this.first === 0 : true;
// }

// }
