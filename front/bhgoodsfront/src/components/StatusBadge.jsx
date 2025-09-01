import React from 'react';

const colors = {
  APROVADO: '#16a34a',
  PENDENTE: '#d97706',
  REJEITADO: '#dc2626'
};

export default function StatusBadge({ status }) {
  const color = colors[status] || '#6b7280';
  return (
    <span style={{
      background: color,
      color: '#fff',
      padding: '2px 8px',
      borderRadius: '12px',
      fontSize: '0.75rem',
      letterSpacing: '0.5px'
    }}>{status}</span>
  );
}
