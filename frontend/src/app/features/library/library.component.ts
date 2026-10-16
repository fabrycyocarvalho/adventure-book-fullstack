import { Component, computed, effect, inject, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Book, BookService } from '../../core';
import { CardComponent } from './card/card.component';
import { DifficyltyFilterComponent } from './filters/difficulty-filter.component';
import { GenreFilterComponent } from './filters/genre-filter.component';
import { SearchEvent, SearchInputComponent } from './filters/search-input.component';

@Component({
  imports: [CardComponent, DifficyltyFilterComponent, GenreFilterComponent, SearchInputComponent],
  selector: 'app-library',
  template: `
    
    <div #caption class="flex flex-col items-center">
      <span class="flex flex-row gap-2 items-center mb-4">
        <img src="books.png" class="icon">
        <p class="text-2xl font-black text-(--app-text)">The Adventure Library</p>
      </span>
      <p>Choose your next adventure from our collection of interactive tales. Each book offers</p>
      <p>unique paths and multiple endings based on your choices.</p>
    </div>

    <app-search-input  (search)="search($event)"></app-search-input>

    <div class="flex items-center justify-between w-full px-14">
      <div class="flex flex-row items-center justify-start gap-2 w-4xl">
        @if(filtered()){
          <img src="filtered.png" class="filter-icon">
        }@else {
          <img src="filter.png" class="filter-icon">
        }
        <span class="mx-2">Filters:</span>
        <app-genre-filter [(selected)]="selectedGenres"></app-genre-filter>
        <app-difficulty-filter [(selected)]="selectedDifficulties"></app-difficulty-filter>
      </div>

      <button (click)="openManageBooks()" class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-stone-700 text-amber-100 font-medium border border-stone-600 shadow-md hover:bg-stone-600 hover:border-stone-500 transition-all duration-200 cursor-pointer">
        Manage Books
      </button>
    </div>
      @if (hasBooks()) {
        <div class="w-full grid grid-cols-[repeat(auto-fill,24rem)] gap-6 pt-2 items-stretch justify-center">
          @for (book of books(); track $index) {
            <app-card [book]="book"></app-card>
          }
        </div>
      }@else {
        <p class="text-(--app-text) text-2xl mt-5">No books available yet!</p>
        <p class="text-(--app-text-secondary) text-sm">To get started, please head over to the Book Management screen and import your first book.</p>
      }
  `,
  host: {
    'class': 'flex flex-col gap-4 items-center'
  },
})
export class LibraryComponent implements OnInit {
  private router = inject(Router);
  private bookService = inject(BookService);
  private books = signal<Book[]>([]);
  protected selectedGenres = signal<string[]>([]);
  protected selectedDifficulties = signal<string[]>([]);
  protected hasBooks = computed(() => this.books().length > 0);
  private term = signal('');

  protected filtered = computed(() =>
    this.term().length > 0 ||
    this.selectedDifficulties().length > 0 ||
    this.selectedGenres().length > 0
  );

  constructor() {
    effect(() => {
      if (this.filtered()) {
        console.log('execute filter');
      }
      this.doSearch();
    });
  }

  ngOnInit(): void {
    this.doSearch();
  }

  search(event: SearchEvent) {
    this.term.set(event.term);
  }

  private doSearch() {
    this.bookService
      .search(this.term(), this.selectedDifficulties(), this.selectedGenres())
      .subscribe(books => this.books.set(books));
  }

  openManageBooks() {
    this.router.navigate(['/manage-books']);
  }
}
