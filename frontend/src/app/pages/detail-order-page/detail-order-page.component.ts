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

import { AfterViewInit, Component } from '@angular/core';
import { AuthStorageService } from '../../services/auth-storage.service';
import { OrderService } from '../../services/order.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-detail-order-page',
  standalone: false,
  templateUrl: './detail-order-page.component.html',
  styles: ``
})
export class DetailOrderPageComponent implements AfterViewInit {

  carts:Array<any> = [];
  billings:Array<any> = [];
  order:any = {}
  loading:boolean = true
  errorMessage:string = ""
  discount:number = 0
  taxes:number = 0
  shipment:number = 0

  constructor(private authStorageService: AuthStorageService, private orderService: OrderService, private router: Router,  private route: ActivatedRoute,){}

  ngAfterViewInit(): void {
    const id = this.route.snapshot.paramMap.get('id') || '';
     this.orderService.detail(parseInt(id)).subscribe({
        next: (res) => {
           setTimeout(() => {
            this.carts = res.data.carts
            this.order = res.data.order
            this.billings = res.data.billings
            this.discount = res.data.discount
            this.taxes = res.data.taxes
            this.shipment = res.data.shipment
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



}
