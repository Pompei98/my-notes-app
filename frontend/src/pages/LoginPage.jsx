import './LoginPage.css';
import React from 'react';
import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

function LoginPage() {
  const apiUrl = import.meta.env.VITE_API_URL;
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const onChangeUsername = (e) => {
    setUsername(e.target.value);
  }
  const onChangePassword = (e) => {
    setPassword(e.target.value);
  }

  const handleLogin = async (e) => {
    e.preventDefault();
    const res = await fetch(`${apiUrl}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });

    if (res.ok) {
      const data = await res.json();
      localStorage.setItem('token', data.token);
      navigate('/home');
    } else {
      alert('none');
    }
  }


  return (
    <div className="login-page">
      <form className="login-form" onSubmit={handleLogin}>
        <input type="text" onChange={onChangeUsername} placeholder='Username' className='login-input' required />
        <input type="password" onChange={onChangePassword} placeholder='Password' className='login-input' required />
        <button type="submit" className="login-button">Login</button>
        <p>or</p>
        <Link to="/register">Register</Link>
      </form>
    </div>
  );
}

export default LoginPage;