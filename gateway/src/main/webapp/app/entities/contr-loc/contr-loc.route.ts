import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContrLocComponent } from './contr-loc.component';
import { ContrLocDetailComponent } from './contr-loc-detail.component';
import { ContrLocPopupComponent } from './contr-loc-dialog.component';
import { ContrLocDeletePopupComponent } from './contr-loc-delete-dialog.component';

import { Principal } from '../../shared';


export const contrLocRoute: Routes = [
  {
    path: 'contr-loc',
    component: ContrLocComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contrLoc.home.title'
    }
  }, {
    path: 'contr-loc/:id',
    component: ContrLocDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contrLoc.home.title'
    }
  }
];

export const contrLocPopupRoute: Routes = [
  {
    path: 'contr-loc-new',
    component: ContrLocPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contrLoc.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'contr-loc/:id/edit',
    component: ContrLocPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contrLoc.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'contr-loc/:id/delete',
    component: ContrLocDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contrLoc.home.title'
    },
    outlet: 'popup'
  }
];
