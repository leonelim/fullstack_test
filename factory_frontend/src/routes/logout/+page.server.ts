import type {Actions} from './$types';
import {redirect} from '@sveltejs/kit';
export const actions = {
  default: async ({request, fetch, cookies}) => {
    const data = await request.formData();
    const jsessionid = cookies.get('JSESSIONID');
    const response = await fetch('http://backend:8080/logout', {
      method: 'POST',
      headers: {
        'Cookie': `JSESSIONID=${jsessionid}`
      }
    });
    redirect(303, '/');
  }
} satisfies Actions;
