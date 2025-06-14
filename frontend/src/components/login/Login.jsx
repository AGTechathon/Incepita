import { useState } from "react";
import './Login.css'; 

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(""); // 🆕 for showing error

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

      console.log(response.status); // should print 200

      if (!response.ok) {
        const errorBody = await response.text(); // Optional: inspect error
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
<<<<<<< HEAD
    console.log("Email:", email);
    console.log("Password:", password);
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h2 className="login-title">Login</h2>
        <form onSubmit={handleSubmit} className="login-form">
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="login-input"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="login-input"
          />
          <button type="submit" className="login-button">
            Login
          </button>
        </form>
      </div>
=======
    setLoginError(""); 

    try {
      const result = await loginUser(email, password);
      console.log("Login successful", result);

      // You can redirect or set global state here
    } catch (err) {
      setLoginError(err.message); // show error message
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

      {/* 🆕 Error message display */}
      {loginError && (
        <div style={styles.error}>
          {loginError}
        </div>
      )}
>>>>>>> 4b4d28e (BE: Implemented user Authentication)
    </div>
  );
};

<<<<<<< HEAD
=======
const styles = {
  container: {
    maxWidth: "400px",
    margin: "50px auto",
    padding: "2rem",
    border: "1px solid #ccc",
    borderRadius: "10px",
    textAlign: "center",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
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

>>>>>>> 4b4d28e (BE: Implemented user Authentication)
export default Login;
