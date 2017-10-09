/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PruebaAzielV2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HistorialDetailComponent } from '../../../../../../main/webapp/app/entities/historial/historial-detail.component';
import { HistorialService } from '../../../../../../main/webapp/app/entities/historial/historial.service';
import { Historial } from '../../../../../../main/webapp/app/entities/historial/historial.model';

describe('Component Tests', () => {

    describe('Historial Management Detail Component', () => {
        let comp: HistorialDetailComponent;
        let fixture: ComponentFixture<HistorialDetailComponent>;
        let service: HistorialService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PruebaAzielV2TestModule],
                declarations: [HistorialDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HistorialService,
                    JhiEventManager
                ]
            }).overrideTemplate(HistorialDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistorialDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistorialService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Historial(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.historial).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
