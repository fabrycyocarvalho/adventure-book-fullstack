import { Component, computed, effect, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { finalize, timer } from 'rxjs';
import { BookQuest, BookService, Consequence, GameSessionService, Option, Section } from '../../core';
import { BtnPlayAgainComponent, TagComponent } from '../../shared';

@Component({
  imports: [TagComponent, BtnPlayAgainComponent],
  selector: 'app-quest',
  styles: `
    :host{
      --app-bg-option: #fbfaf9;
      --app-bg-option-hover: #f2eeeb;

      #sp-button{
        &:disabled{
          background: #e9e4e0;
          color: #a3948c;
          cursor: not-allowed;
          opacity: 0.7;
        }
      }
    }
  `,
  template: `
        <header class="h-18 w-full sticky flex justify-center">
          <div class="flex flex-1 flex-row items-center justify-between">
            <button type="button" (click)="backToLibrary($event)" class="hover:text-(--app-text-secondary-hover) text-(--app-text) flex flex-row text-sm gap-2 items-center justify-center cursor-pointer transition-all duration-200" ><img src="arrow-back.png" class="icon small">Back To Library</button>
            <span>
              <app-tag imageUrl="s-book-gray.png" [value]="book()?.title|| ''"></app-tag>
            </span>
            <button id="sp-button" type="button" [disabled]="hasWon() || isDead()" (click)="saveProgress()" 
              class="flex flex-row gap-2 text-sm px-4 py-1 hover:bg-(--app-bg-hover) text-(--app-text) items-center justify-center cursor-pointer border rounded-lg">
            <img src="save.png" class="icon small">Save Progress</button>            
          </div>
        </header>

        <main class="relative flex-1 overflow-y-auto">
          <div class="border rounded-lg border-(--app-accent) py-4 px-10 text-(--app-text) flex flex-col gap-4">

            <div>{{currentSection()?.text}}</div>
            <hr class="text-(--app-accent)">

            @if (hasWon()) {
            <div class=" flex flex-col items-center justify-center">
              <div class="flex flex-row">
                <img src="fireworks.gif" class="w-14 h-14">
                <p class="text-center text-green-700">Congratulations! You have survived the perils of {{book()?.title}} and emerged victorious.</p>
                <img src="fireworks.gif" class="w-14 h-14">
              </div>
              <btn-play-again (clicked)="playAgain()"></btn-play-again>
            </div> 
            }@else if (isDead()) {
            <div class=" flex flex-col gap-2 items-center justify-center">
              <p class="text-center text-red-700">Game Over. You ran out of health points and could not finish the journey.</p>
              <btn-play-again (clicked)="playAgain()"></btn-play-again>  
            </div>              
            }@else {
            <span class="flex flex-row justify-between items-center">
              <p class="text-lg font-extrabold">What do you choose?</p>
              <div class="flex flex-row items-center">
                @if(tapHealth()!== 0){
                  <p class="text-xs pr-4 animate-pulse opacity-75" [class]="{'text-green-600': tapHealth()>0, 'text-red-500': tapHealth()<0}">{{tapHealth()}} HP</p>
                }
                <span class="text-red-500">&#10084;</span> {{healthPoints()}} HP
              </div>
            </span>

            @for (option of currentSection()?.options; track $index) {
              <a (click)="gotTo(option)" class="flex flex-row items-center gap-4 p-4 border rounded-md border-(--app-accent) bg-(--app-bg-option) hover:bg-(--app-bg-option-hover) hover:border-(--app-accent-border) cursor-pointer">
                <span class="text-xs flex items-center rounded-xl px-4 py-0.5 font-bold text-white bg-(--app-text)">{{$index + 1 }}</span>
                <div class="flex flex-col gap-2">
                  <span class="font-semibold">{{option.description}}</span>
                  <span class=" text-xs font-light text-(--app-text-secondary)">{{option.tip || 'The blue light might be magical, but the passage looks dangerous'}}</span>
                </div>
              </a>
            }
            }
          </div>
        </main>
  `,
})
export class QuestComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private gameSessionService = inject(GameSessionService);

  protected bookId = computed<string>(() => {
    return this.route.snapshot.paramMap.get('bookId')?.toString() || '';
  });

  private book = signal<BookQuest | null>(null);

  protected begin = computed<Section>(() => this.book()?.sections?.find(sec => sec.type === 'BEGIN') as Section);
  protected nodes = computed<Section[]>(() => this.book()?.sections?.filter(sec => sec.type === 'NODE') as Section[]);
  protected ends = computed<Section[]>(() => this.book()?.sections?.filter(sec => sec.type === 'END') as Section[]);

  protected currentSection = signal<Section | null>(null);

  protected loading = signal(false);
  protected inProgress = signal(false);
  protected hasWon = signal(false);
  protected healthPoints = signal(10);
  protected tapHealth = signal(0);
  protected isDead = computed(() => this.healthPoints() <= 0);

  constructor() {
    effect(() => {
      if (this.tapHealth() !== 0) {
        this.startTimer();
      }
    });

    effect(() => {
      if (this.hasWon() || this.isDead()) {
        this.clearProgress();
      }
    })

  }

  ngOnInit(): void {
    const bookId = this.bookId();
    if (bookId) {
      this.bookService
        .get(bookId)
        .pipe(finalize(() => this.initializeQuest()))
        .subscribe(book => {
          this.book.set(book);
        });
    }
  }

  initializeQuest(): void {
    this.gameSessionService.getGameSession(this.bookId()).subscribe(session => {

      if (!session) {
        this.currentSection.set(this.begin());
        return;
      }

      this.inProgress.set(true);
      this.healthPoints.set(session.healthPoints);

      const currentSection = this.getSection(session.currentSectionId);
      if (currentSection) {
        this.currentSection.set(currentSection);
      }
    });
  }

  protected backToLibrary(event: Event) {
    event.stopPropagation();
    history.back();
  }

  gotTo(option: Option) {
    if (!this.checkConsequence(option.consequence)) return;

    const targetId = option.gotoId;

    if (this.ends().some(e => e.id === targetId)) {
      this.hasWon.set(true);
    }

    const nextSection = this.getSection(targetId);

    if (nextSection) {
      this.currentSection.set(nextSection);
    } else {
      console.warn(`Seção com o ID ${targetId} não foi encontrada.`);
    }
  }

  private getSection(targetId: number): Section | undefined {
    return this.ends().find(e => e.id === targetId) ||
      (targetId === this.begin().id ? this.begin() : undefined) ||
      this.nodes().find(node => node.id === targetId);
  }

  checkConsequence(consequence: Consequence | undefined) {
    if (!consequence) return true;

    const points = Number(consequence.value);

    this.healthPoints.update(current =>
      consequence.type === 'GAIN_HEALTH'
        ? current + points
        : current - points
    );

    this.tapHealth.set(consequence.type === 'GAIN_HEALTH' ? points : -points);

    return this.healthPoints() > 0;
  }

  playAgain() {
    this.currentSection.set(this.begin());
    this.inProgress.set(false);
    this.hasWon.set(false);
    this.healthPoints.set(10);
    this.tapHealth.set(0);
  }

  saveProgress() {
    const username = this.gameSessionService.username || prompt('What is your name, adventurer?');
    if (!username) return;

    const currentSectionId = this.currentSection()?.id;
    if (!currentSectionId) return;

    this.gameSessionService
      .saveProgress(this.bookId(), username, this.healthPoints(), currentSectionId)
      .subscribe(() => alert('The progress has been saved successfully!'));
  }

  private clearProgress() {
    this.loading.set(true);
    this.gameSessionService
      .clearProgress()
      .subscribe(() => this.loading.set(false));

  }

  private startTimer() {
    timer(3000).subscribe(() => this.tapHealth.set(0));
  }

}
