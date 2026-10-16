import { ComponentFixture, TestBed } from '@angular/core/testing';
import { QuestChapterComponent } from './quest-chapter.component';

describe('QuestChapterComponent', () => {
  let component: QuestChapterComponent;
  let fixture: ComponentFixture<QuestChapterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestChapterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(QuestChapterComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
