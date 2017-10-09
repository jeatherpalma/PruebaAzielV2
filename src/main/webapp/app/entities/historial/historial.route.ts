import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HistorialComponent } from './historial.component';
import { HistorialDetailComponent } from './historial-detail.component';
import { HistorialPopupComponent } from './historial-dialog.component';
import { HistorialDeletePopupComponent } from './historial-delete-dialog.component';

export const historialRoute: Routes = [
    {
        path: 'historial',
        component: HistorialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.historial.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'historial/:id',
        component: HistorialDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.historial.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historialPopupRoute: Routes = [
    {
        path: 'historial-new',
        component: HistorialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.historial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historial/:id/edit',
        component: HistorialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.historial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historial/:id/delete',
        component: HistorialDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.historial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
