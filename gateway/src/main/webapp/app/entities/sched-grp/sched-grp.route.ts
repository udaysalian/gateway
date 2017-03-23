import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchedGrpComponent } from './sched-grp.component';
import { SchedGrpDetailComponent } from './sched-grp-detail.component';
import { SchedGrpPopupComponent } from './sched-grp-dialog.component';
import { SchedGrpDeletePopupComponent } from './sched-grp-delete-dialog.component';

import { Principal } from '../../shared';


export const schedGrpRoute: Routes = [
  {
    path: 'sched-grp',
    component: SchedGrpComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedGrp.home.title'
    }
  }, {
    path: 'sched-grp/:id',
    component: SchedGrpDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedGrp.home.title'
    }
  }
];

export const schedGrpPopupRoute: Routes = [
  {
    path: 'sched-grp-new',
    component: SchedGrpPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedGrp.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-grp/:id/edit',
    component: SchedGrpPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedGrp.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-grp/:id/delete',
    component: SchedGrpDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedGrp.home.title'
    },
    outlet: 'popup'
  }
];
