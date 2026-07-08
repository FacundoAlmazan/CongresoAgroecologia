import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter, withInMemoryScrolling } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { withHashLocation } from '@angular/router';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes,
    withHashLocation(),
    // Configuración para restaurar el scroll al inicio en cada navegación
      withInMemoryScrolling({
        scrollPositionRestoration: 'enabled'
      }))
  ]
};
