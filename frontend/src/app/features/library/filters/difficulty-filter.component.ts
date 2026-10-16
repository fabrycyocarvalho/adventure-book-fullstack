import { TitleCasePipe } from '@angular/common';
import { Component, model, signal } from '@angular/core';

const DIFFICULTIES: string[] = ['EASY', 'MEDIUM', 'HARD'];

@Component({
  imports: [TitleCasePipe],
  selector: 'app-difficulty-filter',
  template: `
    @for (difficulty of difficulties(); track $index) {
      <button 
          type="button"
          (click)="toggle(difficulty)"
          [class.active]="selected().includes(difficulty)"
          class="px-2 text-(--app-text) text-xs font-black font-mono cursor-pointer bg-(--app-accent) border rounded-xl border-(--app-accent-border)">
          {{ difficulty | titlecase }}
        </button>
    }
  `,
  host: {
    'class': 'flex flex-row gap-2'
  },
  styles: `
  :host{
    button{
      &.active{
          background: var(--color-amber-300);
          color: var(--color-black)
      }
    }
  }
  `
})
export class DifficyltyFilterComponent {
  protected readonly difficulties = signal(DIFFICULTIES);
  selected = model<string[]>([]);

  toggle(difficulty: string) {
    this.selected.update(current =>
      current.includes(difficulty) ? current.filter(d => d !== difficulty) : [...current, difficulty]
    );
  }

}
