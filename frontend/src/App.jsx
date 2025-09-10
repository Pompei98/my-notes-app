import { useState } from 'react'
import Button from '@mui/material/Button';
import './App.css'
import LoginPage from './pages/LoginPage';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/home" element={<HomePage />} />
        <Route path="/" element={<LoginPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
