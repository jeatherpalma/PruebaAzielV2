import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaAzielV2SharedModule } from '../../shared';
import {
    HistorialService,
    HistorialPopupService,
    HistorialComponent,
    HistorialDetailComponent,
    HistorialDialogComponent,
    HistorialPopupComponent,
    HistorialDeletePopupComponent,
    HistorialDeleteDialogComponent,
    historialRoute,
    historialPopupRoute,
} from './';

const ENTITY_STATES = [
    ...historialRoute,
    ...historialPopupRoute,
];

@NgModule({
    imports: [
        PruebaAzielV2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HistorialComponent,
        HistorialDetailComponent,
        HistorialDialogComponent,
        HistorialDeleteDialogComponent,
        HistorialPopupComponent,
        HistorialDeletePopupComponent,
    ],
    entryComponents: [
        HistorialComponent,
        HistorialDialogComponent,
        HistorialPopupComponent,
        HistorialDeleteDialogComponent,
        HistorialDeletePopupComponent,
    ],
    providers: [
        HistorialService,
        HistorialPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaAzielV2HistorialModule {}
