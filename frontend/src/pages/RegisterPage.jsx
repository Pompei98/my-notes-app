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

    const onChangeUsername = (e) => {
        setUsername(e.target.value);
    }
    const onChangePassword = (e) => {
        setPassword(e.target.value);
    }

    const handleReg = async (e) => {
        e.preventDefault();
        const res = await fetch(`${apiUrl}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({  }),
        });

        if (res.ok) {
            const data = await res.json();
            navigate('/home');
        } else {
            alert('none');
        }
    }


    return (
        <div className="register-page">
            <div>
                <Link to="/" className='back-to-login'><FaArrowLeft /></Link>
                <form className="register-form" onSubmit={handleReg}>
                    <input type="text" placeholder='Username' className='register-input' onChange={onChangeUsername} required />
                    <input type="password" placeholder='Password' className='register-input'onChange={onChangePassword} required />
                    <button type="submit" className="register-button">Register</button>
                </form>
            </div>
        </div>
    );
}

export default RegisterPage;