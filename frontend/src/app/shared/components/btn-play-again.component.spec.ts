import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BtnPlayAgainComponent } from './btn-play-again.component';

describe('BtnPlayAgainComponent', () => {
  let component: BtnPlayAgainComponent;
  let fixture: ComponentFixture<BtnPlayAgainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BtnPlayAgainComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BtnPlayAgainComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
