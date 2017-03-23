import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RtSchedValdComponent } from './rt-sched-vald.component';
import { RtSchedValdDetailComponent } from './rt-sched-vald-detail.component';
import { RtSchedValdPopupComponent } from './rt-sched-vald-dialog.component';
import { RtSchedValdDeletePopupComponent } from './rt-sched-vald-delete-dialog.component';

import { Principal } from '../../shared';


export const rtSchedValdRoute: Routes = [
  {
    path: 'rt-sched-vald',
    component: RtSchedValdComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSchedVald.home.title'
    }
  }, {
    path: 'rt-sched-vald/:id',
    component: RtSchedValdDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSchedVald.home.title'
    }
  }
];

export const rtSchedValdPopupRoute: Routes = [
  {
    path: 'rt-sched-vald-new',
    component: RtSchedValdPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSchedVald.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'rt-sched-vald/:id/edit',
    component: RtSchedValdPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSchedVald.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'rt-sched-vald/:id/delete',
    component: RtSchedValdDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSchedVald.home.title'
    },
    outlet: 'popup'
  }
];
