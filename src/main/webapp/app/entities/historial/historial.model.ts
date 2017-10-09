import { BaseEntity } from './../../shared';

export class Historial implements BaseEntity {
    constructor(
        public id?: number,
        public precioactual?: number,
        public cantidad?: number,
        public totalproducto?: number,
        public producto?: BaseEntity,
        public ventas?: BaseEntity,
    ) {
    }
}
