import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BusinessAssociateComponent } from './business-associate.component';
import { BusinessAssociateDetailComponent } from './business-associate-detail.component';
import { BusinessAssociatePopupComponent } from './business-associate-dialog.component';
import { BusinessAssociateDeletePopupComponent } from './business-associate-delete-dialog.component';

import { Principal } from '../../shared';


export const businessAssociateRoute: Routes = [
  {
    path: 'business-associate',
    component: BusinessAssociateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociate.home.title'
    }
  }, {
    path: 'business-associate/:id',
    component: BusinessAssociateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociate.home.title'
    }
  }
];

export const businessAssociatePopupRoute: Routes = [
  {
    path: 'business-associate-new',
    component: BusinessAssociatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociate.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate/:id/edit',
    component: BusinessAssociatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociate.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'business-associate/:id/delete',
    component: BusinessAssociateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.businessAssociate.home.title'
    },
    outlet: 'popup'
  }
];
