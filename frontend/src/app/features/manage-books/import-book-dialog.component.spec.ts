import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ImportBookDialogComponent } from './import-book-dialog.component';

describe('ImportBookDialogComponent', () => {
  let component: ImportBookDialogComponent;
  let fixture: ComponentFixture<ImportBookDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImportBookDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ImportBookDialogComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
