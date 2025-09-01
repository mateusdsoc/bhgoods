import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import RestaurantDetail from '../pages/RestaurantDetail';
import Login from '../pages/Login';
import AdminDashboard from '../pages/AdminDashboard';
import ProtectedRoute from '../components/ProtectedRoute';

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/restaurantes/:id" element={<RestaurantDetail />} />
        <Route path="/login" element={<Login />} />
        <Route path="/admin" element={<ProtectedRoute role="ADMIN"><AdminDashboard /></ProtectedRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
