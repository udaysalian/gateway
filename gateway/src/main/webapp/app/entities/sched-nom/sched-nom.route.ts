import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchedNomComponent } from './sched-nom.component';
import { SchedNomDetailComponent } from './sched-nom-detail.component';
import { SchedNomPopupComponent } from './sched-nom-dialog.component';
import { SchedNomDeletePopupComponent } from './sched-nom-delete-dialog.component';

import { Principal } from '../../shared';


export const schedNomRoute: Routes = [
  {
    path: 'sched-nom',
    component: SchedNomComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedNom.home.title'
    }
  }, {
    path: 'sched-nom/:id',
    component: SchedNomDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedNom.home.title'
    }
  }
];

export const schedNomPopupRoute: Routes = [
  {
    path: 'sched-nom-new',
    component: SchedNomPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedNom.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-nom/:id/edit',
    component: SchedNomPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedNom.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-nom/:id/delete',
    component: SchedNomDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedNom.home.title'
    },
    outlet: 'popup'
  }
];
