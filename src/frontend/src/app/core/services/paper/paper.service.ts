import { Injectable } from '@angular/core';
import { DialogData } from '../../models/dialog-data-model';
import { HttpClient } from '@angular/common/http';
import { Paper } from '../../models/paper-model';
import { Observable } from 'rxjs';
import { ConfigService } from '../config.service';

/**
 * Service for the Papers.
 * @author Alessandro Soro
 * @author Lukas Metzner
 */
@Injectable({
  providedIn: 'root',
})
export class PaperService {

  constructor(private http: HttpClient, private config: ConfigService) { }

  /**
   * Getter for all the papers.
   * @return Observable of the list of Papers
   */
  public getPapers(): Observable<Paper[]> {
    return this.http.get<Paper[]>(`${this.config.getConfig('api_endpoint')}/papers`);
  }

  /**
   * Getter for all the added papers of the user.
   * @return Observable of the list of Papers
   */
  public getAddedPapers(): Observable<Paper[]> {
    return this.http.get<Paper[]>(`${this.config.getConfig('api_endpoint')}/self/addedPapers`);
  }

  /**
   * Adds the paper to the list of papers.
   * @param paperToAdd Paper to add.
   * @return Observable of the List of Papers
   */
  public addPaper(paperToAdd: DialogData): Observable<Paper> {
    return this.http.put<Paper>(`${this.config.getConfig('api_endpoint')}/papers`, paperToAdd);
  }

  /**
   * Adds the paper to the list of liked papers from the user.
   * @param paperToLike Paper to add.
   * @return Observable of the List of Papers
   */
  public addLikedPaper(paperToLike: any): Observable<Paper> {
    return this.http.put<Paper>(`${this.config.getConfig('api_endpoint')}/self/likedPaper`, paperToLike);
  }

  /**
   * Deletes paper from the list of liked papers of the user.
   * @param paperToRemove Paper to delete.
   */
  public removeLikedPaper(paperToRemove: any): void {
    this.http.post<Paper>(`${this.config.getConfig('api_endpoint')}/self/likedPaper`, paperToRemove).subscribe();
  }

  /**
   * Deletes paper from the list of added papers of the user.
   * @param paperToRemove Paper to delete.
   */
  public removeAddedPaper(paperToRemove: any): Observable<Paper> {
    return this.http.post<Paper>(`${this.config.getConfig('api_endpoint')}/self/addedPapers`, paperToRemove);
  }

  /**
   * Getter for liked Paper of the user.
   * @return Observable of the List of Papers
   */
  public async getlikedPapers(): Promise<Observable<Paper[]>> {
    return this.http.get<Paper[]>(`${this.config.getConfig('api_endpoint')}/self/likedPapers`);
  }

  /**
   * Splits the id of the paper from the url.
   * @param url Url of pdf.
   * @return String of the paper id.
   */
  public paperIdFromURL(url: string): string {
    let id: string;
    if (!url.includes('arxiv')) {
      throw new Error('Only arxiv is supported');
    }
    let splitted: string[] = url.split('/');
    id = splitted[splitted.length - 1];

    if (url.includes('pdf')) {
      id = id.substring(0, splitted[splitted.length - 1].length - 4);
    }
    return id;
  }
}
