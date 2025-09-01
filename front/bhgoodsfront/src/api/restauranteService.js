import { request } from './http';

export function listarPublicos({ nome, categorias } = {}) {
  const params = new URLSearchParams();
  if (nome) params.append('nome', nome);
  if (categorias?.length) params.append('categorias', categorias.join(','));
  const qs = params.toString();
  return request(`/api/public/restaurantes${qs ? `?${qs}` : ''}`);
}

export function detalhePublico(id) {
  return request(`/api/public/restaurantes/${id}`);
}

export function listarAdmin({ status, nome, categorias } = {}) {
  const params = new URLSearchParams();
  if (status) params.append('status', status);
  if (nome) params.append('nome', nome);
  if (categorias?.length) params.append('categorias', categorias.join(','));
  const qs = params.toString();
  return request(`/api/admin/restaurantes${qs ? `?${qs}` : ''}`, { auth: true });
}

export function atualizarStatus(id, status) {
  return request(`/api/admin/restaurantes/${id}/status?status=${status}`, { method: 'PUT', auth: true });
}
