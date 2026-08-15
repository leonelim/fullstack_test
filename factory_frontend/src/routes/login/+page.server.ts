import { redirect, fail } from '@sveltejs/kit';
import type {Actions} from './$types';
export const actions = {
  default: async ({request, fetch, cookies}) => {
    const data = await request.formData();
    const response = await fetch('http://backend:8080/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: data.get('id'),
        password: data.get('password')
      }) 
    });
    if (response.ok) {
      const setCookie = response.headers.getSetCookie();
      setCookie.forEach(header => {
        const [nameValue] = header.split(';');
        const [name, value] = nameValue.split('=');
        cookies.set(name, value, {
          path: '/',
          samesite: 'lax'
        });
      });
      redirect(303, '/');
    } else {
      return fail(401, {
          error: "неправильный пароль"
      })
    }
  }
} satisfies Actions;
