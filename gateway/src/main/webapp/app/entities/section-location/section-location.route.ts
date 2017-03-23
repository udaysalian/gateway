import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SectionLocationComponent } from './section-location.component';
import { SectionLocationDetailComponent } from './section-location-detail.component';
import { SectionLocationPopupComponent } from './section-location-dialog.component';
import { SectionLocationDeletePopupComponent } from './section-location-delete-dialog.component';

import { Principal } from '../../shared';


export const sectionLocationRoute: Routes = [
  {
    path: 'section-location',
    component: SectionLocationComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.sectionLocation.home.title'
    }
  }, {
    path: 'section-location/:id',
    component: SectionLocationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.sectionLocation.home.title'
    }
  }
];

export const sectionLocationPopupRoute: Routes = [
  {
    path: 'section-location-new',
    component: SectionLocationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.sectionLocation.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'section-location/:id/edit',
    component: SectionLocationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.sectionLocation.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'section-location/:id/delete',
    component: SectionLocationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.sectionLocation.home.title'
    },
    outlet: 'popup'
  }
];
