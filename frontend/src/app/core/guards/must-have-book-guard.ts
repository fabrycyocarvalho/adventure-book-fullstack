import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const mustHaveBookGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const navState = router.currentNavigation()?.extras?.state;

  console.log('state', state);
  console.log('navState', navState);

  // const hasValidState = navState && Object.keys(navState).length > 0;
  // if (hasValidState) {
  //   return true; // Permite o acesso
  // }
  // return router.createUrlTree(['/']);

  return true;
};
