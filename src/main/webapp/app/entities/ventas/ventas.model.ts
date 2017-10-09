import { BaseEntity } from './../../shared';

export class Ventas implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public totalpagar?: number,
        public ganancia?: number,
        public historials?: BaseEntity[],
    ) {
    }
}
