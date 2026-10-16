import { TitleCasePipe } from '@angular/common';
import { Component, inject, input } from '@angular/core';
import { Router } from '@angular/router';
import { Book, DifficultyBook } from '../../../core';
import { QuestChapterComponent } from '../../../shared/components/quest-chapter.component';
import { QuestTimeComponent } from '../../../shared/components/quest-time.component';
import { TagComponent } from '../../../shared/components/tag.component';

@Component({
  imports: [TagComponent, QuestTimeComponent, QuestChapterComponent, TitleCasePipe],
  selector: 'app-card',
  template: `
    <div class="flex flex-col gap-2">
      @let b = book();
      <div class="caption">
        <p class="text-2xl font-semibold text-(--app-text)">{{b.title}}</p>
        <p>by {{b.author}}
      </div>

      @if(b.description){
        <p class="my-4 leading-6 font-normal">{{b.description}}</p>
      }

      <div class="flex flex-row gap-2">
        <app-tag [value]="b.difficulty | titlecase" [severity]="difficultySeverity(b.difficulty)"></app-tag>
        @if (b.genre) {
          <app-tag [value]="b.genre"></app-tag>
        }
      </div>

      <div class="flex flex-row gap-2">
        @if (b.durationMinutes) {
          <app-quest-time [value]="b.durationMinutes"></app-quest-time>
        }
        <app-quest-chapter [value]="b.chaptersCount"></app-quest-chapter>
      </div>

      <div class="flex flex-row my-2">
      @for (tag of b.tags; track $index) {
        <app-tag [value]="tag" severity="secondary"></app-tag>
      }
      </div>
    </div>

    <button type="button" class="w-full bg-linear-to-r from-yellow-600 via-yellow-500 to-amber-300 hover:from-yellow-700 hover:via-yellow-600 hover:to-amber-400 border rounded-lg p-2 text-white flex flex-row items-center justify-center cursor-pointer transition-all duration-200" 
      (click)="goToQuest(b)">
      <img src="star.png" class="icon small">Begin Quest
  </button>
  `,
  host: {
    'class': 'w-full h-full flex flex-col justify-between gap-2 p-8 border rounded-lg border-(--app-accent-border)'
  },
})
export class CardComponent {

  private router = inject(Router);

  public book = input.required<Book>();

  difficultySeverity(difficulty: DifficultyBook): 'success' | 'warn' | 'danger' {
    if (difficulty === 'EASY') return 'success';
    return difficulty === 'MEDIUM' ? 'warn' : 'danger';
  }

  goToQuest(book: Book) {
    this.router.navigate(['/quest', book.id]);
  }

}
