import { Component, input, output } from '@angular/core';

export type TagSeverity = 'primary' | 'success' | 'warn' | 'danger' | 'secondary';

export interface ClickTagEvent {
  value: string;
}

@Component({
  imports: [],
  selector: 'app-tag',
  template: `
  <span class="flex flex-row gap-2 rounded-xl px-2 py-0.5 tracking-wide text-(--app-text)" [class]="severity()" (click)="onCLick($event)">
    @if(imageUrl()){
      <img [src]="imageUrl()" class="icon small">
    }
    {{value()}}
  </span>`,
  styles: `
    :host{
      .primary {
        background: var(--app-accent);
        border: 1px solid var(--app-accent-border);
        &:hover{
          background: var(--app-accent-hover);
          border: 1px solid var(--app-accent-border-hover);
        }
      }

      .success{
        background: var(--color-green-100);
        border: 1px solid var(--color-green-300);
        &:hover{
          background: var(--color-green-50);
          border: 1px solid var(--color-green-200);
        }
      }

      .warn{
        background: var(--color-amber-100);
        border: 1px solid var(--color-amber-300);
        &:hover{
          background: var(--color-amber-50);
          border: 1px solid var(--color-amber-200);
        }
      }

      .danger{
       background: var(--color-red-100);
        border: 1px solid var(--color-red-300);
        &:hover{
          background: var(--color-red-50);
          border: 1px solid var(--color-red-200);
        }
      }

      .secondary{
       background: var(--color-zinc-200);
        border: 1px solid var(--color-gray-100);
        &:hover{
          background: var(--color-gray-50);
          border: 1px solid var(--color-gray-50);
        }
      }
    }
  `,
  host: {
    'class': 'mx-2 text-xs font-black font-mono cursor-pointer',
  }
})
export class TagComponent {
  public value = input.required<string>();
  public key = input<string>();
  public imageUrl = input<string>();
  public severity = input<TagSeverity>('primary');

  public click = output<ClickTagEvent>();

  onCLick(event: PointerEvent) {
    event.stopPropagation();
    this.click.emit({
      value: this.key() || this.value()
    });
  }
}
