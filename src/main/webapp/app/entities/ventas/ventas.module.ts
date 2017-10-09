import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaAzielV2SharedModule } from '../../shared';
import {
    VentasService,
    VentasPopupService,
    VentasComponent,
    VentasDetailComponent,
    VentasDialogComponent,
    VentasPopupComponent,
    VentasDeletePopupComponent,
    VentasDeleteDialogComponent,
    ventasRoute,
    ventasPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ventasRoute,
    ...ventasPopupRoute,
];

@NgModule({
    imports: [
        PruebaAzielV2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VentasComponent,
        VentasDetailComponent,
        VentasDialogComponent,
        VentasDeleteDialogComponent,
        VentasPopupComponent,
        VentasDeletePopupComponent,
    ],
    entryComponents: [
        VentasComponent,
        VentasDialogComponent,
        VentasPopupComponent,
        VentasDeleteDialogComponent,
        VentasDeletePopupComponent,
    ],
    providers: [
        VentasService,
        VentasPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaAzielV2VentasModule {}
