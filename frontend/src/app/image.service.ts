import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ImageService {

  private apiUrl = 'http://localhost:8080/images';

  constructor(private http: HttpClient) {}

  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(this.apiUrl, formData);
  }

  searchByLabel(label: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/search?label=${label}`
    );
  }
}
