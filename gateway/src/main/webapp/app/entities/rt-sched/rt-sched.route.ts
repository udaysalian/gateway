import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RtSchedComponent } from './rt-sched.component';
import { RtSchedDetailComponent } from './rt-sched-detail.component';
import { RtSchedPopupComponent } from './rt-sched-dialog.component';
import { RtSchedDeletePopupComponent } from './rt-sched-delete-dialog.component';

import { Principal } from '../../shared';


export const rtSchedRoute: Routes = [
  {
    path: 'rt-sched',
    component: RtSchedComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSched.home.title'
    }
  }, {
    path: 'rt-sched/:id',
    component: RtSchedDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSched.home.title'
    }
  }
];

export const rtSchedPopupRoute: Routes = [
  {
    path: 'rt-sched-new',
    component: RtSchedPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSched.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'rt-sched/:id/edit',
    component: RtSchedPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSched.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'rt-sched/:id/delete',
    component: RtSchedDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.rtSched.home.title'
    },
    outlet: 'popup'
  }
];
