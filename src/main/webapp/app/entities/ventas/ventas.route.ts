import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VentasComponent } from './ventas.component';
import { VentasDetailComponent } from './ventas-detail.component';
import { VentasPopupComponent } from './ventas-dialog.component';
import { VentasDeletePopupComponent } from './ventas-delete-dialog.component';

export const ventasRoute: Routes = [
    {
        path: 'ventas',
        component: VentasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.ventas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ventas/:id',
        component: VentasDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.ventas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ventasPopupRoute: Routes = [
    {
        path: 'ventas-new',
        component: VentasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.ventas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ventas/:id/edit',
        component: VentasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.ventas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ventas/:id/delete',
        component: VentasDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pruebaAzielV2App.ventas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
