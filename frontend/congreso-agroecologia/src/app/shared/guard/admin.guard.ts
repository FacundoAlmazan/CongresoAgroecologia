import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { TipoRol } from '../model/TipoRol.enum.model';

export const adminGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.estaLogueado()) return router.createUrlTree(['/login']);
  return authService.tieneRol(TipoRol.ADMIN)
    ? true
    : router.createUrlTree(['/home']);
};
