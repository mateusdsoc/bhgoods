const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function getToken() {
  return localStorage.getItem('token');
}

export async function request(path, { method = 'GET', auth = false, headers = {}, body } = {}) {
  const finalHeaders = { 'Content-Type': 'application/json', ...headers };
  if (auth) {
    const token = getToken();
    if (token) finalHeaders['Authorization'] = `Bearer ${token}`;
  }
  const resp = await fetch(`${API_BASE}${path}`, {
    method,
    headers: finalHeaders,
    body: body ? JSON.stringify(body) : undefined,
  });
  if (resp.status === 401) {
    localStorage.removeItem('token');
    throw new Error('Não autorizado. Faça login novamente.');
  }
  if (!resp.ok) {
    const text = await resp.text();
    throw new Error(text || 'Erro na requisição');
  }
  const contentType = resp.headers.get('content-type');
  if (contentType && contentType.includes('application/json')) {
    return resp.json();
  }
  return null;
}

export { API_BASE };
