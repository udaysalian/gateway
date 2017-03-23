import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NomPrtyComponent } from './nom-prty.component';
import { NomPrtyDetailComponent } from './nom-prty-detail.component';
import { NomPrtyPopupComponent } from './nom-prty-dialog.component';
import { NomPrtyDeletePopupComponent } from './nom-prty-delete-dialog.component';

import { Principal } from '../../shared';


export const nomPrtyRoute: Routes = [
  {
    path: 'nom-prty',
    component: NomPrtyComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nomPrty.home.title'
    }
  }, {
    path: 'nom-prty/:id',
    component: NomPrtyDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nomPrty.home.title'
    }
  }
];

export const nomPrtyPopupRoute: Routes = [
  {
    path: 'nom-prty-new',
    component: NomPrtyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nomPrty.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'nom-prty/:id/edit',
    component: NomPrtyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nomPrty.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'nom-prty/:id/delete',
    component: NomPrtyDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nomPrty.home.title'
    },
    outlet: 'popup'
  }
];
