import React, { useState } from 'react';

// categorias poderiam vir da API futuramente
const CATEGORIES = [
  'PIZZA','JAPONESA','BRASILEIRA','VEGANA','LANCHES','SOBREMESAS'
];

export default function RestaurantFilters({ onChange, showStatus = false }) {
  const [nome, setNome] = useState('');
  const [categorias, setCategorias] = useState([]);
  const [status, setStatus] = useState('');

  function toggleCategoria(cat) {
    setCategorias(prev => prev.includes(cat) ? prev.filter(c => c !== cat) : [...prev, cat]);
  }

  function submit(e) {
    e.preventDefault();
    onChange({ nome: nome || undefined, categorias, status: status || undefined });
  }

  function clear() {
    setNome(''); setCategorias([]); setStatus('');
    onChange({});
  }

  return (
    <form onSubmit={submit} style={{ display: 'flex', flexDirection: 'column', gap: 8, marginBottom: 16 }}>
      <input placeholder="Nome" value={nome} onChange={e => setNome(e.target.value)} />
      {showStatus && (
        <select value={status} onChange={e => setStatus(e.target.value)}>
          <option value="">-- Status --</option>
          <option value="PENDENTE">Pendente</option>
          <option value="APROVADO">Aprovado</option>
          <option value="REJEITADO">Rejeitado</option>
        </select>
      )}
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: 6 }}>
        {CATEGORIES.map(cat => (
          <button key={cat} type="button" onClick={() => toggleCategoria(cat)}
            style={{
              padding: '4px 10px',
              borderRadius: 16,
              border: '1px solid #ccc',
              background: categorias.includes(cat) ? '#2563eb' : '#f3f4f6',
              color: categorias.includes(cat) ? '#fff' : '#111',
              cursor: 'pointer'
            }}>{cat}</button>
        ))}
      </div>
      <div style={{ display: 'flex', gap: 8 }}>
        <button type="submit">Filtrar</button>
        <button type="button" onClick={clear}>Limpar</button>
      </div>
    </form>
  );
}
