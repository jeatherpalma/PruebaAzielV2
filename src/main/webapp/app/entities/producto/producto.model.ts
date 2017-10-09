import { BaseEntity } from './../../shared';

export class Producto implements BaseEntity {
    constructor(
        public id?: number,
        public cbarras?: string,
        public nombre?: string,
        public descripcion?: string,
        public cantidad?: number,
        public pcompra?: number,
        public pventa?: number,
        public historials?: BaseEntity[],
        public categoria?: BaseEntity,
    ) {
    }
}
