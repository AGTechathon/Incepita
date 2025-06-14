import React from 'react';
import './Sidebar.css';
import { Link } from 'react-router-dom';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <div>
        
        <nav className="sidebar-nav">
          <Link to="/mypaper" className="sidebar-link">MyPaper</Link>
          <Link to="/generate" className="sidebar-link">Generate</Link>
        </nav>
      </div>
      <div className="sidebar-bottom">
        <button className="logout-button">Logout</button>
      </div>
    </div>
  );
};

export default Sidebar;
