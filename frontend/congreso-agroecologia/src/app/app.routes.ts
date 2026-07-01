import { Routes } from '@angular/router';
import { PanelUsuario } from './shared/component/panel-usuario/panel-usuario';
import { Home } from './shared/component/home/home';
import { FormularioPaper } from './shared/component/formulario-paper/formulario-paper';
import { PanelControl } from './shared/component/panel-control/panel-control';
import { PanelEvaluaciones } from './shared/component/panel-evaluaciones/panel-evaluaciones';
import { PanelAutor } from './shared/component/panel-autor/panel-autor';
import { Cronograma } from './shared/component/cronograma/cronograma';

export const routes: Routes = [
{ path: '', redirectTo: 'home', pathMatch: 'full' },
{path:'home', component:Home},
 { path: 'panel_usuario', component: PanelUsuario },
 {path:'formulario_paper', component: FormularioPaper },
 { path: 'formulario_paper_edicion/:id', component: FormularioPaper },
{path:'panel_control', component: PanelControl },
{path:'panel_evaluaciones', component: PanelEvaluaciones },
{path:'panel_autor', component: PanelAutor },
{ path: 'cronograma', component: Cronograma },
//{path:'', component:  },
];
