import type {Actions, PageLoad } from './$types'
export const actions = {
  default: async ({request, fetch, cookies}) => {
    const data = await request.formData();
    const jsessionid = cookies.get('JSESSIONID');
    const response = await fetch('http://backend:8080/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Cookie': `JSESSIONID=${jsessionid}`
      },
      body: JSON.stringify({
        id: data.get('id'),
        password: data.get('password')
      })
    }
  );
  }
} satisfies Actions;
export const load: PageLoad = async ({fetch, cookies}) => {
    const jsessionid = cookies.get('JSESSIONID');
    const response = await fetch('http://backend:8080/users', {
      headers: {
      'Cookie': `JSESSIONID=${jsessionid}`
    }
  }
    );
    if (response.ok) {
        return {
            users: await response.json()
        }
    }
}
