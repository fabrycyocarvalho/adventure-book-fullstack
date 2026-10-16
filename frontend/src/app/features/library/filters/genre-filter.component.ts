import { Component, inject, model, OnInit, signal } from '@angular/core';
import { BookService } from '../../../core';

@Component({
  imports: [],
  selector: 'app-genre-filter',
  template: `
    @for (genre of genres(); track $index) {
      <button 
          type="button"
          (click)="toggle(genre)"
          [class.active]="selected().includes(genre)"
          class="px-2 text-(--app-text) text-xs font-black font-mono cursor-pointer bg-(--app-accent) border rounded-xl border-(--app-accent-border)">
          {{ genre }}
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
export class GenreFilterComponent implements OnInit {
  protected genres = signal<string[]>([]);
  public selected = model<string[]>([]);

  private bookService = inject(BookService);

  ngOnInit(): void {
    this.bookService.getGenres().subscribe(genres => this.genres.set(genres));
  }

  toggle(genre: string) {
    this.selected.update(current =>
      current.includes(genre) ? current.filter(g => g !== genre) : [...current, genre]
    );
  }

}
