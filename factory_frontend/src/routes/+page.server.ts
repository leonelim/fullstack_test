import type {PageLoad} from './$types'
export const load: PageServerLoad = async ({fetch, cookies}) => {
  const jsessionid = cookies.get('JSESSIONID');
  const response = await fetch('http://backend:8080/current-user',
    {
      headers: {
        'Cookie': `JSESSIONID=${jsessionid}`
      }
    }
  );
  if (response.ok) {
    let res = await response.json();
    console.log(res);
    return res;
  } else {
    return {
      id: null
    }
  }
}
