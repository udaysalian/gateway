import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContactComponent } from './contact.component';
import { ContactDetailComponent } from './contact-detail.component';
import { ContactPopupComponent } from './contact-dialog.component';
import { ContactDeletePopupComponent } from './contact-delete-dialog.component';

import { Principal } from '../../shared';


export const contactRoute: Routes = [
  {
    path: 'contact',
    component: ContactComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contact.home.title'
    }
  }, {
    path: 'contact/:id',
    component: ContactDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contact.home.title'
    }
  }
];

export const contactPopupRoute: Routes = [
  {
    path: 'contact-new',
    component: ContactPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contact.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'contact/:id/edit',
    component: ContactPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contact.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'contact/:id/delete',
    component: ContactDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'netraApp.contact.home.title'
    },
    outlet: 'popup'
  }
];
