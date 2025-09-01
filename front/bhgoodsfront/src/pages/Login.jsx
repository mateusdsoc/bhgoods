import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const { login } = useAuth();
  const nav = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [erro, setErro] = useState(null);
  const [loading, setLoading] = useState(false);

  async function submit(e) {
    e.preventDefault();
    try {
      setLoading(true); setErro(null);
      await login(email, password);
      nav('/admin');
    } catch (e) { setErro(e.message); } finally { setLoading(false); }
  }

  return (
    <div style={{ maxWidth: 400, margin: '60px auto', padding: 24, border: '1px solid #e5e7eb', borderRadius: 8 }}>
      <h1>Login</h1>
      <form onSubmit={submit} style={{ display: 'flex', flexDirection: 'column', gap: 12 }}>
        <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} type="email" required />
        <input placeholder="Senha" value={password} onChange={e => setPassword(e.target.value)} type="password" required />
        <button type="submit" disabled={loading}>{loading ? 'Entrando...' : 'Entrar'}</button>
        {erro && <p style={{ color: 'red', fontSize: 14 }}>{erro}</p>}
      </form>
    </div>
  );
}
