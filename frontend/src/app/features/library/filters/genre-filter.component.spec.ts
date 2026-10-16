import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DifficyltyFilterComponent } from './difficulty-filter.component';

describe('DifficyltyFilterComponent', () => {
  let component: DifficyltyFilterComponent;
  let fixture: ComponentFixture<DifficyltyFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DifficyltyFilterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DifficyltyFilterComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
