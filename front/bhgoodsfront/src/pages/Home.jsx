import React, { useEffect, useState } from 'react';
import { listarPublicos } from '../api/restauranteService';
import RestaurantFilters from '../components/RestaurantFilters';
import RestaurantList from '../components/RestaurantList';

export default function Home() {
  const [dados, setDados] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  async function carregar(filtros={}) {
    try {
      setLoading(true); setErro(null);
      const res = await listarPublicos(filtros);
      setDados(res);
    } catch (e) {
      setErro(e.message);
    } finally { setLoading(false); }
  }

  useEffect(() => { carregar(); }, []);

  return (
    <div style={{ maxWidth: 960, margin: '0 auto', padding: 24 }}>
      <h1>Restaurantes</h1>
      <RestaurantFilters onChange={carregar} />
      {loading && <p>Carregando...</p>}
      {erro && <p style={{ color: 'red' }}>{erro}</p>}
      <RestaurantList restaurantes={dados} />
    </div>
  );
}
