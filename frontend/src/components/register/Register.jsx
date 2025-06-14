import React, { useState } from 'react';
import './Register.css';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    fullname: '',
    email: '',
  });

  const [registerError, setRegisterError] = useState('');
  const [registerSuccess, setRegisterSuccess] = useState('');

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  async function registerUser({ username, password, email, fullname }) {
    const url = `http://localhost:8080/api/auth/register`;

    const headers = {
      'Content-Type': 'application/json',
    };

    const body = {
      username,
      password,
      email,
      fullName: fullname,
    };

    const response = await fetch(url, {
      method: 'POST',
      headers,
      body: JSON.stringify(body),
    });

    if (!response.ok) {
      const errorBody = await response.text();
      throw new Error(errorBody || 'Registration failed.');
    }

    return await response.text(); // Assuming the server returns a plain success message
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setRegisterError('');
    setRegisterSuccess('');

    try {
      const result = await registerUser(formData);
      setRegisterSuccess(result || 'Registered successfully!');
      setFormData({ username: '', password: '', fullname: '', email: '' });
    } catch (error) {
      setRegisterError(error.message);
    }
  };

  return (
    <div className="register-page">
      <div className="register-container">
        <h2 className="register-title">Register</h2>
        <form onSubmit={handleSubmit} className="register-form">
          <input
            type="text"
            name="fullname"
            placeholder="Full Name"
            className="register-input"
            value={formData.fullname}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="username"
            placeholder="Username"
            className="register-input"
            value={formData.username}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            className="register-input"
            value={formData.email}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            className="register-input"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <button type="submit" className="register-button">
            Register
          </button>
        </form>

        {registerError && <p className="error-msg">{registerError}</p>}
        {registerSuccess && <p className="success-msg">{registerSuccess}</p>}
      </div>
    </div>
  );
};

export default Register;
