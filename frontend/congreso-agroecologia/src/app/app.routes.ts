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


export const routes: Routes = [
{ path: '', redirectTo: 'home', pathMatch: 'full' },

 { path: 'formulario_paper_edicion/:id', component: FormularioPaper },
{ path: 'home', component:Home},
{ path: 'panel_usuario', component: PanelUsuario },
{ path: 'formulario_paper', component: FormularioPaper },
{ path: 'panel_control', component: PanelControl },
{ path: 'panel_evaluaciones', component: PanelEvaluaciones },
{ path: 'panel_autor', component: PanelAutor },
{ path: 'cronograma', component: Cronograma },
{ path: 'login', component: Login },
{ path: 'registro', component: Registro },
{ path: 'inscripcion-congreso', component: InscripcionCongreso },
{ path: 'asignar_evaluadores', component: AsignarEvaluadores },
{ path: 'asignacion_trabajo', component: AsignacionTrabajo },
{ path: 'evaluaciones', component: Evaluaciones },
{ path: 'resultado_evaluacion', component: ResultadoEvaluacion },
{ path: 'formulario_evaluacion', component: FormularioEvaluacion },
{ path: 'peticiones', component: VistaAsignacion },
{ path: 'formulario_asignacion', component: FormularioAsignacion },
//{path:'', component:  },
];
