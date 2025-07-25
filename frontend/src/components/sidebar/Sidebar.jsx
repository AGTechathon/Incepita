import React from 'react';
import './Sidebar.css';
import { Link } from 'react-router-dom';

const Sidebar = () => {

    const getGenerateLink = () =>{
        return "/" + localStorage.getItem("username") + "/generatepaper";
  };
  
      const getPaperLink = () =>{
        return "/" + localStorage.getItem("username") + "/papers";
    };


    const doLogout = () => {
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        window.location.href = "/";
    };

  return (
    <div className="sidebar">
      <div>
        <nav className="sidebar-nav">
          <Link to="/" className="sidebar-brand">NextGenPaper</Link>
          <Link to={getPaperLink()} className="sidebar-link">MyPaper</Link>
          <Link to={getGenerateLink()} className="sidebar-link">Generate</Link>
        </nav>
      </div>
      <div className="sidebar-bottom">
        <button onClick={() => doLogout()} className="logout-button">Logout</button>
      </div>
    </div>
  );
};

export default Sidebar;
