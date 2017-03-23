import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchedEventComponent } from './sched-event.component';
import { SchedEventDetailComponent } from './sched-event-detail.component';
import { SchedEventPopupComponent } from './sched-event-dialog.component';
import { SchedEventDeletePopupComponent } from './sched-event-delete-dialog.component';

import { Principal } from '../../shared';


export const schedEventRoute: Routes = [
  {
    path: 'sched-event',
    component: SchedEventComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedEvent.home.title'
    }
  }, {
    path: 'sched-event/:id',
    component: SchedEventDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedEvent.home.title'
    }
  }
];

export const schedEventPopupRoute: Routes = [
  {
    path: 'sched-event-new',
    component: SchedEventPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedEvent.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-event/:id/edit',
    component: SchedEventPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedEvent.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sched-event/:id/delete',
    component: SchedEventDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.schedEvent.home.title'
    },
    outlet: 'popup'
  }
];
