import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Trabajo } from "../model/trabajo.model";
import { Observable } from "rxjs";


@Injectable({
    providedIn:'root'
})
export class TrabajoService{
    private apiUrl:string='/api/trabajos';

    constructor(private http: HttpClient){
        
    }

    listar(): Observable<Trabajo[]> {
        return this.http.get<Trabajo[]>(this.apiUrl);
      }
    
      crear(trabajo: Trabajo): Observable<Trabajo> {
        return this.http.post<Trabajo>(this.apiUrl, trabajo);
      }
      modificar(id: number, trabajo: Trabajo): Observable<Trabajo> {
        return this.http.put<Trabajo>(`${this.apiUrl}/${id}`, trabajo);
      }
      get(id: number): Observable<Trabajo> {
        return this.http.get<Trabajo>(`${this.apiUrl}/${id}`);
      }
    
    
      eliminar(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
      }
      enviar(id:number): Observable<Trabajo>{
        return this.http.put<Trabajo>(`${this.apiUrl}/${id}/enviar`,'');
      }
}