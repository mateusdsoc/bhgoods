import React from 'react';
import StatusBadge from './StatusBadge';

export default function RestaurantList({ restaurantes, admin = false, onStatus }) {
  if (!restaurantes.length) return <p>Nenhum restaurante.</p>;
  return (
    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
      <thead>
        <tr>
          <th style={th}>Nome</th>
          <th style={th}>Categorias</th>
          {admin && <th style={th}>Status</th>}
          {!admin && <th style={th}>Ações</th>}
          {admin && <th style={th}>Ações</th>}
        </tr>
      </thead>
      <tbody>
        {restaurantes.map(r => (
          <tr key={r.id} style={{ borderBottom: '1px solid #eee' }}>
            <td style={td}>{r.nome}</td>
            <td style={td}>{r.categorias?.join(', ')}</td>
            {admin && <td style={td}><StatusBadge status={r.statusAprovacao} /></td>}
            <td style={td}>
              {!admin && <a href={`/restaurantes/${r.id}`}>Detalhes</a>}
              {admin && (
                <div style={{ display: 'flex', gap: 6 }}>
                  <button disabled={r.statusAprovacao==='APROVADO'} onClick={() => onStatus(r.id,'APROVADO')}>Aprovar</button>
                  <button disabled={r.statusAprovacao==='REJEITADO'} onClick={() => onStatus(r.id,'REJEITADO')}>Rejeitar</button>
                </div>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

const th = { textAlign: 'left', padding: 8, background: '#f1f5f9', fontWeight: 600 };
const td = { padding: 8, fontSize: 14 };
