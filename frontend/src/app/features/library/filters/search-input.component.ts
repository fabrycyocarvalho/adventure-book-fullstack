import { Component, OnDestroy, output } from '@angular/core';
import { debounceTime, distinctUntilChanged, Subject, Subscription } from 'rxjs';


export interface SearchEvent {
  term: string;
}

@Component({
  imports: [],
  selector: 'app-search-input',
  styles: `
  
  :host{
      input[name="search"] {
        width: 100%;
        box-sizing: border-box;
        border: 1px solid var(--app-accent-border);
        color: var(--app-text);
        font-weight: 500;
        border-radius: 12px;
        font-size: 14px;
        background-color: var(--app-accent); 
        background-image: url('/search-icon.png');
        background-position: 8px 6px; 
        background-repeat: no-repeat;
        padding: 8px 28px 6px 40px;
        outline: none;

        &:focus-visible {
          border: 1px solid #d9a726;
          outline: none;
          box-shadow: 0 0 0 3px rgba(240, 196, 67, 0.25);
        }
      }
  }

  `,
  template: `
    <div class="flex flex-col items-center">
      <div class="w-lg">
       <input type="text" name="search" placeholder="Search..." (input)="onSearchInput($event)">
      </div>
    </div>  
  `,
})
export class SearchInputComponent implements OnDestroy {

  private searchSubject = new Subject<string>();
  private subscription: Subscription;

  public search = output<SearchEvent>();

  constructor() {
    this.subscription = this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
    ).subscribe(value => this.search.emit({ term: value }));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onSearchInput(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.searchSubject.next(valor);
  }
}
