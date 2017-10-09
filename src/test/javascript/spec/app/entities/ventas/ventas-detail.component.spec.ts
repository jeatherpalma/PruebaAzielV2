/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PruebaAzielV2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VentasDetailComponent } from '../../../../../../main/webapp/app/entities/ventas/ventas-detail.component';
import { VentasService } from '../../../../../../main/webapp/app/entities/ventas/ventas.service';
import { Ventas } from '../../../../../../main/webapp/app/entities/ventas/ventas.model';

describe('Component Tests', () => {

    describe('Ventas Management Detail Component', () => {
        let comp: VentasDetailComponent;
        let fixture: ComponentFixture<VentasDetailComponent>;
        let service: VentasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PruebaAzielV2TestModule],
                declarations: [VentasDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VentasService,
                    JhiEventManager
                ]
            }).overrideTemplate(VentasDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VentasDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VentasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ventas(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ventas).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
