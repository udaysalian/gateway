import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NomComponent } from './nom.component';
import { NomDetailComponent } from './nom-detail.component';
import { NomPopupComponent } from './nom-dialog.component';
import { NomDeletePopupComponent } from './nom-delete-dialog.component';

import { Principal } from '../../shared';


export const nomRoute: Routes = [
  {
    path: 'nom',
    component: NomComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nom.home.title'
    }
  }, {
    path: 'nom/:id',
    component: NomDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nom.home.title'
    }
  }
];

export const nomPopupRoute: Routes = [
  {
    path: 'nom-new',
    component: NomPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nom.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'nom/:id/edit',
    component: NomPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nom.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'nom/:id/delete',
    component: NomDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.nom.home.title'
    },
    outlet: 'popup'
  }
];
