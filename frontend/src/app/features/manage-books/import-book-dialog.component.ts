import { DialogRef } from '@angular/cdk/dialog';
import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';

@Component({
  selector: 'app-import-book-dialog',
  imports: [CommonModule],
  template: `
    <div class="bg-stone-800 text-amber-100 p-6 rounded-xl border border-stone-700 shadow-2xl max-w-md w-full">
      <h2 class="text-xl font-bold mb-2 flex items-center gap-2">
        Import Book JSON
      </h2>
      <p class="text-stone-400 text-sm mb-4">
        Select a valid JSON file containing the book data to import.
      </p>

      <div class="mb-6">
        <label class="flex flex-col items-center justify-center border-2 border-dashed border-stone-600 rounded-lg p-6 cursor-pointer hover:border-amber-400 transition-colors bg-stone-900/50">
          <span class="text-sm text-stone-300 font-medium mb-1">
            @if(noSelectedFileName()){
              Click to choose the JSON file
            } @else {
              {{selectedFileName()}}
            }
          </span>
          <input type="file" accept=".json" (change)="onFileSelected($event)" class="hidden" />
        </label>
      </div>

      
      <div class="flex justify-end gap-3">
        <button 
          (click)="dialogRef.close()" 
          class="px-4 py-2 rounded-lg bg-stone-700 text-stone-300 hover:bg-stone-600 transition-all text-sm font-medium cursor-pointer">
          Cancel
        </button>
        <button 
          [disabled]="noSelectedFileName()"
          (click)="import()" 
          class="px-4 py-2 rounded-lg bg-amber-500 text-stone-950 font-semibold hover:bg-amber-400 disabled:opacity-50 disabled:cursor-not-allowed transition-all text-sm cursor-pointer">
          Import
        </button>
      </div>
    </div>
  `
})
export class ImportBookDialogComponent {
  private dialogRef = inject(DialogRef<any>);

  protected selectedFile = signal<File | null>(null);
  protected selectedFileName = signal<string | null>(null);

  protected noSelectedFileName = computed(() => !this.selectedFileName());

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile.set(file);
      this.selectedFileName.set(file.name);
    }
  }

  import() {
    if (this.selectedFile()) {
      this.dialogRef.close(this.selectedFile());
    }
  }
}
