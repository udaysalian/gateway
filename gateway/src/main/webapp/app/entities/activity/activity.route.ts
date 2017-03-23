import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityPopupComponent } from './activity-dialog.component';
import { ActivityDeletePopupComponent } from './activity-delete-dialog.component';

import { Principal } from '../../shared';


export const activityRoute: Routes = [
  {
    path: 'activity',
    component: ActivityComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.activity.home.title'
    }
  }, {
    path: 'activity/:id',
    component: ActivityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.activity.home.title'
    }
  }
];

export const activityPopupRoute: Routes = [
  {
    path: 'activity-new',
    component: ActivityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.activity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'activity/:id/edit',
    component: ActivityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.activity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'activity/:id/delete',
    component: ActivityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.activity.home.title'
    },
    outlet: 'popup'
  }
];
