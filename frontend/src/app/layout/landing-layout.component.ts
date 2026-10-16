import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BookStatsService } from '../core/services/book-stats.service';

@Component({
  imports: [RouterOutlet],
  selector: 'app-layout-library',
  template: `
   <div class="flex h-screen overflow-hidden font-serif">

      <div class="flex-1 flex flex-col min-w-0 overflow-hidden">
        
        <header class="h-60 flex flex-col items-center gap-4 px-30 pt-12 sticky top-0 z-40 text-(--color-text-header) bg-(--bg-header-library) bg-header-library">

        <div class="flex flex-col gap-8 items-center mx-12">
          <div class="flex flex-row gap-2 items-center">
            <img src="sparkle-3.png" class="icon huge"/>
            <p class="text-5xl font-bold text-(--color-title-header) text-shadow-sm">Adventure Awaits</p>
            <img src="sparkle-3.png" class="icon huge"/>
          </div>
          <span class="text-center font-medium text-shadow-lg/20">
            <p>Embark on epic quests where every choice shapes your destinby. Explore mystical</p>
            <p>realms, solve ancient mysteries, and become the hero of your own story.</p>
          </span>
        </div>
        <div class="flex flex-row text-sm gap-2 items-center">
          <img src="s-book.png" class="icon">
          <span class="text-sm font-normal">{{booksSize()}} Epic Adventures Available</span>
          <img src="s-sparkle.png" class="icon">
        </div>                  

        </header>

        <main class="relative flex-1 overflow-y-auto">
          <div class="max-w-350 mx-auto px-4 py-6 sm:px-6 lg:px-10 lg:py-8">
            <router-outlet></router-outlet>
          </div>
        </main>
      </div>   
   </div>
   `,
  styles: `
    :host {
      --bg-header-library: #5f4f45;
      --color-title-header: #eebd5d;
      --color-text-header: #dedad7;
    }
    .bg-header-library{
      background-image: linear-gradient(to bottom, rgba(50, 40, 35, 0.85), rgba(50, 40, 35, 0.85)), url('/bg-adventure.png');
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
    }
  `,
})
export class LandingLayoutComponent {

  protected booksSize = inject(BookStatsService).booksSize;

}
