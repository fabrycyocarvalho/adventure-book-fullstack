import { ComponentFixture, TestBed } from '@angular/core/testing';
import { QuestTimeComponent } from './quest-time.component';

describe('QuestTimeComponent', () => {
  let component: QuestTimeComponent;
  let fixture: ComponentFixture<QuestTimeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestTimeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(QuestTimeComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
