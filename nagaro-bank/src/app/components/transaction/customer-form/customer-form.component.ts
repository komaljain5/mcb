import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { TransactionService } from 'src/app/services/transaction/transaction.service';
import * as customerdata from 'src/assets/data/customer.json';




@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.scss']
})
export class CustomerFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;
  regionList: any=['Port Louis','Curepipe','Vacoas','Port Mathurin']
  currencyList:any=['AED','EUR','CHF','MUR','USD']
  
  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private transactionService: TransactionService
  ) { }

  ngOnInit() {
      
      this.form = this.formBuilder.group({
          transactionType: ['', Validators.required],
          customerNumber: ['', Validators.required],
          customerAddress: ['', Validators.required],
          customerPhoneNumber: ['', Validators.required],
          amount: ['', Validators.required],
          currency: ['', Validators.required],
          beneficiaryBank: ['', Validators.required],
          beneficiaryACNumber: ['', Validators.required],
          paymentDetails: ['', Validators.required],
          cardDetail: ['', Validators.required],
          region: ['', Validators.required],
          customerName: ['', [Validators.required]]
      });
      
  }
  customer:any=null
  
  get f() { return this.form.controls; }

  customerFormData(inputData:string){
      var json= (customerdata as any).default;
      this.customer=json.responseXML.getCustomerInfoResponse.getCustomerInfoResult.CUST_INFO;
      
      
      if(this.customer.CUST_NO==inputData){
          console.log("user found");
          this.form.patchValue({
              customerName:this.customer.SHORT_NAME,
              customerAddress:this.customer.STREET_ADDR,
              customerPhoneNumber:this.customer.CONTACT_INFO_V7.CONTACT_INFO_V7.PHONE_LIST_V7.PHONE_LIST_ITEM_V7.PHONE
          })  
          }else{
              console.log("user not found");
              this.form.patchValue({
                  customerName:'',
                  customerAddress:'',
                  customerPhoneNumber:''
              }) 
          }
  }

  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.form.invalid) {
          return;
      }

      this.loading = true;
      this.transactionService.createTransaction(this.form.value)
          .pipe(first())
          .subscribe(
              data => {
                //  this.alertService.success('Transaction successfully submited', { keepAfterRouteChange: true });
                  this.router.navigate(['/viewTransaction']);
              },
              error => {
                  //this.alertService.error(error);
                  this.loading = false;
              });
  }
}