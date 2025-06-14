import React from 'react';
import './Sidebar.css';
import { Link } from 'react-router-dom';

const Sidebar = () => {

    const getUsername = () =>{
        return "/" + localStorage.getItem("username") + "/generatepaper";
    }

  return (
    <div className="sidebar">
      <div>
        <nav className="sidebar-nav">
          <Link to="/" className="sidebar-brand">NextGenPaper</Link>
          <Link to="/mypaper" className="sidebar-link">MyPaper</Link>
          <Link to={getUsername()} className="sidebar-link">Generate</Link>
        </nav>
      </div>
      <div className="sidebar-bottom">
        <button className="logout-button">Logout</button>
      </div>
    </div>
  );
};

export default Sidebar;
