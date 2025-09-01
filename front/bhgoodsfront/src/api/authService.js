import { request } from './http';

export async function login(email, password) {
  const data = await request('/api/auth/login', {
    method: 'POST',
    body: { email, password },
  });
  // Assumindo que retorno tem campo token
  if (data?.token) {
    localStorage.setItem('token', data.token);
  }
  return data;
}

export function logout() {
  localStorage.removeItem('token');
}

export function getToken() {
  return localStorage.getItem('token');
}
