import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaPropostasComponent } from './consulta-propostas.component';

describe('ConsultaPropostasComponent', () => {
  let component: ConsultaPropostasComponent;
  let fixture: ComponentFixture<ConsultaPropostasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultaPropostasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaPropostasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
