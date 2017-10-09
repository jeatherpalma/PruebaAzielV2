import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PruebaAzielV2CategoriaModule } from './categoria/categoria.module';
import { PruebaAzielV2ProductoModule } from './producto/producto.module';
import { PruebaAzielV2VentasModule } from './ventas/ventas.module';
import { PruebaAzielV2HistorialModule } from './historial/historial.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PruebaAzielV2CategoriaModule,
        PruebaAzielV2ProductoModule,
        PruebaAzielV2VentasModule,
        PruebaAzielV2HistorialModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaAzielV2EntityModule {}
