import { useState } from "react";
import './Login.css'; // Optional if you want external styles

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(""); // For showing login error

  async function loginUser(username, password) {
    let url = `http://localhost:8080/api/auth/login`;

    let headers = {
      'Content-Type': 'application/json',
    };

    let body = {
      username,
      password,
    };

    try {
      let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
      });

      console.log("Response status:", response.status);

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error("Invalid username or password.");
      }

      let data = await response.json();
      localStorage.setItem("token", data.token);
      localStorage.setItem("username", data.user.username);
      return data;
    } catch (error) {
      console.error("Error during login:", error);
      throw error;
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoginError(""); // Clear any previous error

    try {
      const result = await loginUser(email, password);
      console.log("Login successful", result);

      // Redirect or set global state here
    } catch (err) {
      setLoginError(err.message);
    }
  };

  return (
    <div style={styles.container}>
      <h2>Login</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          type="text"
          placeholder="username"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          style={styles.input}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          style={styles.input}
        />
        <button type="submit" style={styles.button}>
          Login
        </button>
      </form>

      {loginError && (
        <div style={styles.error}>
          {loginError}
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    width: "370px",
    margin: "50px auto",
    padding: "30px",
    textAlign: "center",
    borderRadius: "12px",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
    backgroundColor: "#1e1e1e"
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "1rem",
  },
  input: {
    padding: "10px",
    fontSize: "16px",
    borderRadius: "5px",
    border: "1px solid #ccc",
  },
  button: {
    padding: "10px",
    fontSize: "16px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
  error: {
    marginTop: "1rem",
    color: "red",
    fontWeight: "bold",
  },
};

export default Login;
