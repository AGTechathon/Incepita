// src/components/Navbar.jsx
import { Link } from 'react-router-dom';
import { Routes, Route } from "react-router-dom";
import Button from '../button/Button.jsx';
import Login from '../login/Login.jsx';
import Register from '../register/Register.jsx';





const Navbar = () => {
  return (
   <nav style={styles.nav}>
      <Link to="/" style={{ ...styles.logo, textDecoration: 'none', color: 'inherit' }}>
        <h2 style={{ margin: 0 }}>NextGenPaper</h2>
      </Link>

      <div style={styles.links}>
        <Link to="/login" style={styles.link}>
          <Button text="Login" />
        </Link>

        <Link to="/register" style={styles.link}>
          <Button text="Register" />
        </Link>

      </div>
    </nav>
  );
};

const styles = {
  nav: {
    padding: '1rem 2rem',
    backgroundColor: '#333',
    color: '#fff',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    
  },
  logo: {
    margin: 0
  },
  links: {
    display: 'flex',
    gap: '1rem'
  },
  link: {
    color: '#fff',
    textDecoration: 'none'

  }
};

export default Navbar;
