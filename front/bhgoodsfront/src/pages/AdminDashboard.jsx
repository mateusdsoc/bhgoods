import React, { useEffect, useState } from 'react';
import RestaurantFilters from '../components/RestaurantFilters';
import RestaurantList from '../components/RestaurantList';
import { listarAdmin, atualizarStatus } from '../api/restauranteService';
import { useAuth } from '../context/AuthContext';

export default function AdminDashboard() {
  const { logout } = useAuth();
  const [dados, setDados] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);
  const [filtros, setFiltros] = useState({});

  async function carregar(f = filtros) {
    try {
      setLoading(true); setErro(null);
      const res = await listarAdmin(f);
      setDados(res);
    } catch (e) { setErro(e.message); } finally { setLoading(false); }
  }

  useEffect(() => { carregar(); /* eslint-disable-next-line */ }, []);

  async function onStatus(id, status) {
    try {
      await atualizarStatus(id, status);
      carregar();
    } catch (e) { setErro(e.message); }
  }

  function onChange(f) {
    setFiltros(f);
    carregar(f);
  }

  return (
    <div style={{ maxWidth: 1100, margin: '0 auto', padding: 24 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Admin - Restaurantes</h1>
        <button onClick={logout}>Sair</button>
      </div>
      <RestaurantFilters onChange={onChange} showStatus />
      {loading && <p>Carregando...</p>}
      {erro && <p style={{ color: 'red' }}>{erro}</p>}
      <RestaurantList restaurantes={dados} admin onStatus={onStatus} />
    </div>
  );
}
