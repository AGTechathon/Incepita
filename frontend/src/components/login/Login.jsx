import { useState } from "react";
import "./Login.css";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState("");
  const navigate = useNavigate();

  async function loginUser(username, password) {
    try {
      const response = await fetch(`http://localhost:8080/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error("Invalid username or password.");
      }

      const data = await response.json();
      localStorage.setItem("token", data.token);
      localStorage.setItem("username", data.user.username);
      return data;
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoginError("");

    try {
      const result = await loginUser(email, password);
      navigate(`/${result.user.username}/generatepaper`);
    } catch (err) {
      setLoginError(err.message);
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h2 className="login-title">Login</h2>
        <form className="login-form" onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="login-input"
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="login-input"
            required
          />
          <button type="submit" className="login-button">
            Login
          </button>
        </form>
        {loginError && (
          <div style={{ marginTop: "1rem", color: "red", fontWeight: "bold" }}>
            {loginError}
          </div>
        )}
      </div>
    </div>
  );
};

export default Login;
