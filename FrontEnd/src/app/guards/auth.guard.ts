import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if(!auth.isAuthenticated()) {
    console.log("not connected");
      router.navigateByUrl('/home')
      return false
  } else {
    console.log("connected");
  }
  return true
};
