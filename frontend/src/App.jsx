import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import Navbar from "./components/navbar/Navbar";
import Login from './components/login/Login.jsx';
import Register from "./components/register/Register.jsx";
import Sidebar from './components/sidebar/Sidebar.jsx';
import Homepage from './components/homepage/Homepage.jsx';

function App() {
  const location = useLocation();
  const hideSidebarRoutes = ['/login', '/register', '/'];

  const shouldShowSidebar = !hideSidebarRoutes.includes(location.pathname);
  // const shouldShowNavbar = location.pathname !== '/';

  return (
    <>
      <Navbar />
      {/* {shouldShowNavbar && <Navbar />} */}
      {shouldShowSidebar && <Sidebar />}

      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/* <Route path="/mypaper" element={<MyPaper />} />
        <Route path="/generate" element={<Generate />} /> */}
      </Routes>
    </>
  );
}

export default App;
