import { Component, Input } from '@angular/core';
import { Usuario } from '../../../model/usuario.model';
import { TipoRol } from '../../../model/TipoRol.enum.model';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../service/usuario.service';

@Component({
  selector: 'app-control-card',
  imports: [CommonModule],
  templateUrl: './control-card.html',
  styleUrl: './control-card.css',
})
export class ControlCard {


  mensajeError='';
  TipoRol=TipoRol;
  @Input() user!:Usuario;
  permisos={
    esOrganizador:false,
    esAdmin:false,
    esEvaluador:false,
    esActivo:false,
  }
  cambios=false;
  estilos={
    activo:'bg-primary text-on-primary shadow-sm active:scale-95',
    inactivo:'border-2 border-outline-variant/50 text-outline-variant hover:bg-surface-container-low'
  }
  
    constructor(private usuarioService: UsuarioService) {}
ngOnInit() {
  this.iniciarPermiso();
}
iniciarPermiso(){
  this.permisos.esAdmin=false;
  this.permisos.esEvaluador=false;
  this.permisos.esOrganizador=false;
  this.permisos.esActivo=this.user.activo;
  this.user.roles.forEach(rol=>{this.cambiarPermiso(rol.nombre)});
  this.cambios=false;
}

  cambiarPermiso(rol :TipoRol ){
    this.cambios=true;

  switch (rol) {
    case TipoRol.ADMIN:
      this.permisos.esAdmin = !this.permisos.esAdmin;
      break;

    case TipoRol.EVALUADOR:
      this.permisos.esEvaluador = !this.permisos.esEvaluador;
      break;

    case TipoRol.OC:
      this.permisos.esOrganizador = !this.permisos.esOrganizador;
      break;
  }
  }
  activar(){
    this.cambios=true;
    this.permisos.esActivo=!this.permisos.esActivo;
  }

  guardar() {
  if (!this.user.id) {
    return;
  }

  this.actualizarRol(TipoRol.ADMIN, this.permisos.esAdmin);
  this.actualizarRol(TipoRol.EVALUADOR, this.permisos.esEvaluador);
  this.actualizarRol(TipoRol.OC, this.permisos.esOrganizador);
  if(this.permisos.esActivo!=this.user.activo){
    this.permisos.esActivo
    ? this.usuarioService.activar(this.user.id)
    : this.usuarioService.desactivar(this.user.id);
  }
  this.iniciarPermiso();
  
}

private actualizarRol(tipoRol: TipoRol, debeTener: boolean): void {
  const tieneRol = this.user.roles.some(r => r.nombre === tipoRol);

  // No hubo cambios
  if (tieneRol === debeTener) {
    return;
  }

  if (debeTener) {
    this.usuarioService.asignarRol(this.user.id!, tipoRol).subscribe({
      next: () => {
        this.user.roles.push({ nombre: tipoRol });
      },
      error: (err) => {
        console.error(`Error al asignar el rol ${tipoRol}`, err);
        //this.mensajeError="No se pudo cambiar los permisos";
      }
    });
  } else {
    this.usuarioService.quitarRol(this.user.id!, tipoRol).subscribe({
      next: () => {
        this.user.roles = this.user.roles.filter(r => r.nombre !== tipoRol);
      },
      error: (err) => {
        console.error(`Error al quitar el rol ${tipoRol}`, err);
        //this.mensajeError="No se pudo cambiar los permisos";
        
      }
    });
  }
  
}

  cancelar(){
    this.iniciarPermiso();
    this.cambios=false;
  }

}
