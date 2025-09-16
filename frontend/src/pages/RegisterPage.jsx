import './RegisterPage.css';
import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';

function RegisterPage() {
    const apiUrl = import.meta.env.VITE_API_URL;
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');


    return (
        <div className="register-page">
            <Link to="/" className='back-to-login'><FaArrowLeft /></Link>
            <form className="register-form">
                <input type="text" placeholder='Username' className='register-input' required />
                <input type="password" placeholder='Password' className='register-input' required />
                <button type="submit" className="register-button">Register</button>
            </form>
        </div>
    );
}

export default RegisterPage;