// src/components/Navbar.jsx
import { Link } from "react-router-dom";
import { Routes, Route } from "react-router-dom";
import Button from "../button/Button.jsx";
import Login from "../login/Login.jsx";
import Register from "../register/Register.jsx";

const Navbar = () => {
  const username = localStorage.getItem("username");
  const isLoggedIn = !!username;

  const doLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("token");
    window.location.href = "/";
  }

  const getWelcomeUrl = () => {
    return username ? `/${username}/papers` : "/welcome";
  }
  return (
    <nav style={styles.nav}>
      <Link
        to="/"
        style={{ ...styles.logo, textDecoration: "none", color: "inherit" }}
      >
        <h2 style={{ margin: 0 }}>NextGenPaper</h2>
      </Link>

      {!isLoggedIn && (
        <div style={styles.links}>
          <Link to="/login" style={styles.link}>
            <Button text="Login" />
          </Link>
          <Link to="/register" style={styles.link}>
            <Button text="Register" />
          </Link>
        </div>
      )}

      {isLoggedIn &&  (
        <div className="links" style={styles.links}>
          <Link to={getWelcomeUrl()} style={styles.link}>
            <Button text={`Welcome, ${username}`} />
          </Link>
          <Link style={styles.link}>
            <Button onClick={() => doLogout()} text="Logout" />
          </Link>
        </div>
      )}
    </nav>
  );
};

const styles = {
  nav: {
    backgroundColor: "rgba(10, 15, 51, 0.6)",
    backdropFilter: "blur(8px)",
    color: "#ffffff",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "1rem 0 ",
    boxShadow: "0 2px 10px rgba(0, 255, 224, 0.08)",
    borderBottom: "1px solid rgba(255, 255, 255, 0.08)",
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    zIndex: 100,
  },
  link: {
    color: "#A3EFFF",
    textDecoration: "none",
    marginLeft: "20px",
    fontWeight: 500,
    transition: "color 0.3s ease",
    marginRight: "20px",
  },
   logo: {
    margin: 0,
    fontWeight: "bold",
    fontSize: "1.5rem",
    marginLeft: "20px",
  }
};


export default Navbar;
