import { Routes } from '@angular/router';
import { PanelUsuario } from './shared/component/panel-usuario/panel-usuario';
import { Home } from './shared/component/home/home';
import { FormularioPaper } from './shared/component/formulario-paper/formulario-paper';
import { PanelControl } from './shared/component/panel-control/panel-control';
import { PanelEvaluaciones } from './shared/component/panel-evaluaciones/panel-evaluaciones';
import { PanelAutor } from './shared/component/panel-autor/panel-autor';
import { Cronograma } from './shared/component/cronograma/cronograma';
import { Login } from './shared/component/login/login';
import { Registro } from './shared/component/registro/registro';
import { InscripcionCongreso } from './shared/component/inscripcion-congreso/inscripcion-congreso';
import { AsignarEvaluadores } from './shared/component/OC/asignar-evaluadores/asignar-evaluadores';
import { AsignacionTrabajo } from './shared/component/OC/asignacion-trabajo/asignacion-trabajo';
import { Evaluaciones } from './shared/component/evaluaciones/evaluaciones';
import { ResultadoEvaluacion } from './shared/component/resultado-evaluacion/resultado-evaluacion';
import { FormularioEvaluacion } from './shared/component/formulario-evaluacion/formulario-evaluacion';
import { VistaAsignacion } from './shared/component/panel-evaluaciones/vista-asignacion/vista-asignacion';
import { FormularioAsignacion } from './shared/component/panel-evaluaciones/formulario-asignacion/formulario-asignacion';
import { authGuard } from './shared/guard/auth.guard';
import { adminGuard } from './shared/guard/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'login', component: Login },
  { path: 'registro', component: Registro },
  { path: 'cronograma', component: Cronograma },

  { path: 'panel_usuario', component: PanelUsuario, canActivate: [authGuard] },
  { path: 'formulario_paper', component: FormularioPaper, canActivate: [authGuard] },
  { path: 'formulario_paper_edicion/:id', component: FormularioPaper, canActivate: [authGuard] },
  { path: 'panel_autor', component: PanelAutor, canActivate: [authGuard] },
  { path: 'inscripcion-congreso', component: InscripcionCongreso, canActivate: [authGuard] },
  { path: 'panel_evaluaciones', component: PanelEvaluaciones, canActivate: [authGuard] },
  { path: 'evaluaciones', component: Evaluaciones, canActivate: [authGuard] },
  { path: 'resultado_evaluacion', component: ResultadoEvaluacion, canActivate: [authGuard] },
  { path: 'formulario_evaluacion', component: FormularioEvaluacion, canActivate: [authGuard] },
  { path: 'peticiones', component: VistaAsignacion, canActivate: [authGuard] },
  { path: 'formulario_asignacion/:id', component: FormularioAsignacion, canActivate: [authGuard] },

  { path: 'panel_control', component: PanelControl, canActivate: [adminGuard] },
  { path: 'asignar_evaluadores', component: AsignarEvaluadores, canActivate: [adminGuard] },
  { path: 'asignacion_trabajo/:id', component: AsignacionTrabajo, canActivate: [adminGuard] },

  { path: '**', redirectTo: 'home' }
];
