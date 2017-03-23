import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BusinessAssociateContactComponent } from './business-associate-contact.component';
import { BusinessAssociateContactDetailComponent } from './business-associate-contact-detail.component';
import { BusinessAssociateContactPopupComponent } from './business-associate-contact-dialog.component';
import { BusinessAssociateContactDeletePopupComponent } from './business-associate-contact-delete-dialog.component';

import { Principal } from '../../shared';


export const businessAssociateContactRoute: Routes = [
  {
    path: 'business-associate-contact',
    component: BusinessAssociateContactComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateContact.home.title'
    }
  }, {
    path: 'business-associate-contact/:id',
    component: BusinessAssociateContactDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateContact.home.title'
    }
  }
];

export const businessAssociateContactPopupRoute: Routes = [
  {
    path: 'business-associate-contact-new',
    component: BusinessAssociateContactPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateContact.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate-contact/:id/edit',
    component: BusinessAssociateContactPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateContact.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate-contact/:id/delete',
    component: BusinessAssociateContactDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociateContact.home.title'
    },
    outlet: 'popup'
  }
];
