import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/Login.css';

function Login() {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', credentials);
    console.log("Login response:", response.data);

    const { token, account, username } = response.data;
    const accountId = account?.id;

    if (token && accountId && username) {
      localStorage.setItem('token', token);
      localStorage.setItem('accountId', accountId);
      localStorage.setItem('username', username); // ✅ Needed for Dashboard
      navigate('/dashboard');
    } else {
      alert('Login failed: Missing token or account info.');
    }
  } catch (error) {
    console.error('Login error:', error);
    alert('Invalid username or password.');
  }
};
;

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Login</h2>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={credentials.username}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={credentials.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
