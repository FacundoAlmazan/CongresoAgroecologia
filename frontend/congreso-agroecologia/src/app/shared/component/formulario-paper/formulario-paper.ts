import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Trabajo } from '../../model/trabajo.model';
import { TipoTrabajo } from '../../model/TipoTrabajo.enum.model';
import { EstadoTrabajo } from '../../model/EstadoTrabajo.enum.model';
import { AutorTrabajo } from '../../model/autorTrabajo.model';
import { CommonModule } from '@angular/common';
import { TrabajoService } from '../../service/trabajo.service';

@Component({
  selector: 'app-formulario-paper',
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-paper.html',
  styleUrl: './formulario-paper.css',
})
export class FormularioPaper implements OnInit{
   
visible: string = 'hidden';

private esEdicion:boolean=false;

  
  paper!: Trabajo ;
// Error local para mostrar mensajes de validación en la interfaz
  mensajeError: string = '';

constructor(
    private route: ActivatedRoute,
    private router: Router,
    private trabajoService: TrabajoService
  ) {}




  // Función para evaluar si un objeto AutorTrabajo individual es válido para enviar
  validarAutor(autor: AutorTrabajo): boolean {
    if (!autor) return false;
    
    // Validar campos obligatorios según tu interfaz (nombre, apellido, email)
    const tieneNombre = autor.nombre && autor.nombre.trim() !== '';
    const tieneApellido = autor.apellido && autor.apellido.trim() !== '';
    const tieneEmail = autor.email && autor.email.trim() !== '';
    
    // Validación básica de formato de correo electrónico
    const emailValido = tieneEmail && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(autor.email);

    return !!(tieneNombre && tieneApellido && emailValido);
  }



ngOnInit(): void {
    // Captura el parámetro 'id' de la URL de forma síncrona al cargar el componente
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.esEdicion = true;
      const id = Number(idParam);
      
      // Obtienes el objeto real desde el servicio (o API) asegurando la persistencia
      this.trabajoService?.get(id).subscribe({
        next: (data) => this.paper = data,
        error: (err) => console.error('Error al cargar el trabajo', err)
      });
    } else {
      this.esEdicion = false;
      this.inicializarObjetoVacio();
    }
  }

  private inicializarObjetoVacio(): void {
    this.paper = {
    titulo: '',    
    tipo: TipoTrabajo.RELATO_EXPERIENCIA,    
    ejesTematicos: [],
  autores:[],  
  };
  
  }
abrirAutor(){this.visible=''}
cerrarAutor(){this.visible='hidden'}

  //CRUD 
  enviarFormulario(): void {
    this.mensajeError = '';
    console.log(this.paper);
    // Validar que el objeto principal y los autores cumplan con los requisitos básicos
    if (!this.paper.titulo || this.paper.titulo.trim() === '') {
      this.mensajeError = 'El título del trabajo es obligatorio.';
      return;
    }


    if (this.esEdicion && this.paper.id) {
      this.modificar();
    } else {
      this.crear();
    }
  }

  // Lógica para enviar un nuevo paper a través de un método POST
  private crear(): void {
    this.trabajoService.crear(this.paper).subscribe({
      next: (trabajoCreado) => {
        console.log('Trabajo creado con éxito:', trabajoCreado);
        // Redirección a la lista de trabajos o pantalla de confirmación
        this.router.navigate(['/trabajos']);
      },
      error: (err) => {
        console.error('Error en el servicio al crear:', err);
        this.mensajeError = 'Ocurrió un error al intentar registrar el trabajo técnico.';
      }
    });
  }

  // Lógica para actualizar un paper existente utilizando un método PUT
  private modificar(): void {
    // Al conocer con certeza que el ID existe, forzamos su conversión de tipo si es necesario
    const id = this.paper.id!;

    this.trabajoService.modificar(id, this.paper).subscribe({
      next: (trabajoModificado) => {
        console.log('Trabajo actualizado con éxito:', trabajoModificado);
        // Redirección tras la actualización exitosa
        this.router.navigate(['/trabajos']);
      },
      error: (err) => {
        console.error('Error en el servicio al modificar:', err);
        this.mensajeError = 'Ocurrió un error al intentar guardar los cambios del trabajo.';
      }
    });
  }
  
}

