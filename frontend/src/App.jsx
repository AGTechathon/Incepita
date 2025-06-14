import Navbar from "./components/navbar/Navbar"
import { Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import Login from './components/login/Login.jsx';

function App() {
 
  return(
     <>
      <Navbar />
      <Routes>
        {/* <Route path="/" element={<Home />} /> */}
        <Route path="/login" element={<Login />} />
      </Routes>
    </>
  )
}

export default App
