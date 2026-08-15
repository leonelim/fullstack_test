import { redirect } from '@sveltejs/kit';
import type {Actions} from './$types'
export const actions = {
    default: async ({fetch, request, cookies}) => {
        const jsessionid = cookies.get('JSESSIONID');
        const response = await fetch('http://backend:8080/change-password',
            {
                method: 'post',
                headers: {
                    'Content-Type': 'text/plain',
                    'Cookie': `JSESSIONID=${jsessionid}`
                },
                body: (await request.formData()).get('password')
            }
        )
        if (response.ok) {
            redirect(303, '/');
        }
        return null;
    }
} satisfies Actions;
