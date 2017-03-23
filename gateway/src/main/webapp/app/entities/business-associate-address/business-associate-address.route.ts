import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BusinessAssociateAddressComponent } from './business-associate-address.component';
import { BusinessAssociateAddressDetailComponent } from './business-associate-address-detail.component';
import { BusinessAssociateAddressPopupComponent } from './business-associate-address-dialog.component';
import { BusinessAssociateAddressDeletePopupComponent } from './business-associate-address-delete-dialog.component';

import { Principal } from '../../shared';


export const businessAssociateAddressRoute: Routes = [
  {
    path: 'business-associate-address',
    component: BusinessAssociateAddressComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateAddress.home.title'
    }
  }, {
    path: 'business-associate-address/:id',
    component: BusinessAssociateAddressDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateAddress.home.title'
    }
  }
];

export const businessAssociateAddressPopupRoute: Routes = [
  {
    path: 'business-associate-address-new',
    component: BusinessAssociateAddressPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateAddress.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate-address/:id/edit',
    component: BusinessAssociateAddressPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateAddress.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate-address/:id/delete',
    component: BusinessAssociateAddressDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateAddress.home.title'
    },
    outlet: 'popup'
  }
];
