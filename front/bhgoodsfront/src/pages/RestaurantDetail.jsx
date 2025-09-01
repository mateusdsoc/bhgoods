import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { detalhePublico } from '../api/restauranteService';

export default function RestaurantDetail() {
  const { id } = useParams();
  const [restaurante, setRestaurante] = useState(null);
  const [erro, setErro] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    async function load() {
      try {
        setLoading(true); setErro(null);
        const data = await detalhePublico(id);
        setRestaurante(data);
      } catch (e) { setErro(e.message); }
      finally { setLoading(false); }
    }
    load();
  }, [id]);

  if (loading) return <p>Carregando...</p>;
  if (erro) return <p style={{ color: 'red' }}>{erro}</p>;
  if (!restaurante) return <p>Não encontrado.</p>;

  return (
    <div style={{ maxWidth: 800, margin: '0 auto', padding: 24 }}>
      <h1>{restaurante.nome}</h1>
      <p><strong>Categorias:</strong> {restaurante.categorias?.join(', ')}</p>
      {restaurante.cardapio && (
        <div style={{ marginTop: 24 }}>
          <h2>Cardápio</h2>
          {restaurante.cardapio.itens?.length ? (
            <ul>
              {restaurante.cardapio.itens.map(item => (
                <li key={item.id}>
                  <strong>{item.nome}</strong> - R$ {(item.preco/100).toFixed(2)}
                  {item.descricao && <div style={{ fontSize: 12 }}>{item.descricao}</div>}
                </li>
              ))}
            </ul>
          ) : <p>Nenhum item.</p>}
        </div>
      )}
    </div>
  );
}
