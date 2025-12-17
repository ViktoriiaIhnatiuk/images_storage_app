import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ImageService } from './image.service';
import { interval, Subscription } from 'rxjs';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h2>Upload image</h2>

    <input type="file" (change)="onFileSelected($event)" />
    <button type="button" (click)="upload()">Upload</button>

    <p *ngIf="uploadResult" style="color: green;">
      {{ uploadResult }}
    </p>

    <p *ngIf="errorMessage" style="color: red;">
      {{ errorMessage }}
    </p>

    <hr />

    <h2>Search images by label</h2>

    <input [(ngModel)]="label" placeholder="label" />
    <button type="button" (click)="search()">Search</button>

    <p *ngIf="loading">Loading...</p>

    <p *ngIf="!loading && images.length === 0 && label">
      No images found
    </p>

    <div *ngFor="let img of images" style="margin: 16px 0;">
      <p>{{ img.fileName }} ({{ img.status }})</p>
      <img
        *ngIf="img.status === 'READY'"
        [src]="img.url"
        width="300"
      />
    </div>
  `
})
export class AppComponent {

  selectedFile?: File;

  uploadResult = '';
  errorMessage = '';

  label = '';
  images: any[] = [];
  loading = false;

  constructor(
    private imageService: ImageService,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      this.selectedFile = input.files[0];
    }
  }

  upload(): void {
    if (!this.selectedFile) return;

    this.uploadResult = '';
    this.errorMessage = '';

    this.imageService.uploadImage(this.selectedFile).subscribe({
      next: () => {
        this.zone.run(() => {
          this.uploadResult = 'Image uploaded. Processing started.';
          this.selectedFile = undefined;
          this.cdr.detectChanges();
        });
      },
      error: () => {
        this.zone.run(() => {
          this.errorMessage = 'Unexpected server error';
          this.cdr.detectChanges();
        });
      }
    });
  }

  search(): void {
    if (!this.label.trim()) return;

    this.loading = true;
    this.images = [];
    this.cdr.detectChanges();

    this.imageService.searchByLabel(this.label).subscribe({
      next: res => {
        this.zone.run(() => {
          this.images = res;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: () => {
        this.zone.run(() => {
          this.errorMessage = 'Search failed';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }
}
