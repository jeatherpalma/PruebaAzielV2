import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ventas } from './ventas.model';
import { VentasService } from './ventas.service';

@Injectable()
export class VentasPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ventasService: VentasService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.ventasService.find(id).subscribe((ventas) => {
                    if (ventas.fecha) {
                        ventas.fecha = {
                            year: ventas.fecha.getFullYear(),
                            month: ventas.fecha.getMonth() + 1,
                            day: ventas.fecha.getDate()
                        };
                    }
                    this.ngbModalRef = this.ventasModalRef(component, ventas);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ventasModalRef(component, new Ventas());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ventasModalRef(component: Component, ventas: Ventas): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ventas = ventas;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
