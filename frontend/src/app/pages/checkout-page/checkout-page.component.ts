/**
 * This file is part of the Sandy Andryanto Online Store Website.
 *
 * @author     Sandy Andryanto <sandy.andryanto.official@gmail.com>
 * @copyright  2025
 *
 * For the full copyright and license information,
 * please view the LICENSE.md file that was distributed
 * with this source code.
 */

import { AfterContentInit, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from '../../services/order.service';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-checkout-page',
  standalone: false,
  templateUrl: './checkout-page.component.html',
  styles: ``
})
export class CheckoutPageComponent implements AfterContentInit {

  payment:number = 0
  accept:boolean = false
  loading:boolean = true
  formData: FormGroup;
  errorMessage:string = ""
  order:any = {}
  carts:Array<any> = [];
  payments:Array<any> = [];
  discount:number = 0
  shipment:number = 0
  taxes:number = 0
  user:any = {}
  private readonly router = inject(Router);

  constructor(
    private fb: FormBuilder,
    private orderService: OrderService,
    private sharedService: SharedService
  )
  {
     this.formData = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      city: ['', [Validators.required]],
      country: ['', [Validators.required]],
      zipCode: ['', [Validators.required]],
      address: ['', [Validators.required]],
      notes: ['', []]
    });
  }


  get firstName() {
    return this.formData.get('firstName');
  }

  get lastName() {
    return this.formData.get('lastName');
  }

  get email() {
    return this.formData.get('email');
  }

  get phone() {
    return this.formData.get('phone');
  }

  get city() {
    return this.formData.get('city');
  }

  get country() {
    return this.formData.get('country');
  }

  get zipCode() {
    return this.formData.get('zipCode');
  }

  get address() {
    return this.formData.get('address');
  }

  setAccept(event:any){
    const e = event
    e.preventDefault();
    e.stopImmediatePropagation();
    this.accept = e.target.checked
  }

  setPayment(event:any, index:number){
    const e = event
    e.preventDefault();
    e.stopImmediatePropagation();
    this.payment = index
  }

  ngAfterContentInit(): void {
    this.loading = true
    this.orderService.checkoutInitial().subscribe({
        next: (res) => {
          setTimeout(() => {
            let order =  res.data.order

            order = {
              ...order,
              subtotal: parseFloat(order.subtotal).toFixed(2),
              totalDiscount: parseFloat(order.totalDiscount).toFixed(2),
              totalPaid: parseFloat(order.totalPaid).toFixed(2),
              totalTaxes: parseFloat(order.totalTaxes).toFixed(2)
            }

            this.order = order
            this.carts = res.data.carts
            this.user = res.data.user
            this.payments = res.data.payments
            this.discount = parseFloat(res.data.discount)
            this.shipment = parseFloat(res.data.shipment)
            this.taxes = parseFloat(res.data.taxes)

            let userData = res.data.user
            delete userData.id
            delete userData.image
            delete userData.gender
            delete userData.status
            delete userData.createdAt
            delete userData.updatedAt
            userData = {
               ...userData,
               notes: ''
            }

            this.payment = res.data.order.paymentId
            this.formData.setValue(userData);
            this.loading = false
          }, 1500)
        },
        error: (err) => {
          const message = err.error?.message || 'Something went wrong';
          this.errorMessage = message
          this.loading = false
        }
      });
  }

  async onSubmit() {

      if (this.formData.invalid) {
        this.errorMessage = 'Form is invalid'
        return;
      }

      const result = await Swal.fire({
        title: 'Confirm Checkout ?',
        text: 'Are you sure you want to place your order?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Yes, Checkout',
        cancelButtonText: 'Cancel',
        showLoaderOnConfirm: true,
        allowOutsideClick: () => !Swal.isLoading(),
        preConfirm: async () => {
          try {

            let form = this.formData.value
            form = {
              ...form,
              paymentId: this.payment
            }

            const res = await this.orderService.checkoutSubmit(form).toPromise();
            return res;
          } catch (error:any) {
            if (error.error?.errors) {
                const messages = this.flattenErrors(error.error.errors);
                this.errorMessage = JSON.stringify(messages)
              }
          }
        }
      });

      if (result.isConfirmed) {
        Swal.fire({
          icon: 'success',
          title: 'Checkout Success',
          text: 'Your order been successfully checkout.'
        });
        this.sharedService.triggerLoadData()
        setTimeout(() => {
            this.router.navigate([`order/list`]);
        }, 1500)

    }
  }

   flattenErrors(errorObj: { [key: string]: string[] }): string[] {
      return Object.values(errorObj).flat();
    }



}
