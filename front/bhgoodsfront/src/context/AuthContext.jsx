import React, { createContext, useContext, useEffect, useState } from 'react';
import { login as apiLogin, logout as apiLogout, getToken } from '../api/authService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(getToken());
  const [user, setUser] = useState(null); // Futuro: decodificar claims se necessário

  async function login(email, password) {
    const data = await apiLogin(email, password);
    setToken(data.token);
    // se resposta incluir role/userId, setUser
    if (data.role || data.userId) setUser({ role: data.role, userId: data.userId });
    return data;
  }

  function logout() {
    apiLogout();
    setToken(null);
    setUser(null);
  }

  const value = { token, user, login, logout, isAuthenticated: !!token };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
