import Navbar from "./components/navbar/Navbar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Login from './components/login/Login.jsx';
import Register from "./components/register/Register.jsx";
import Sidebar from './components/sidebar/Sidebar.jsx';
import GeneratePaper from "./components/generatepaper/GeneratePaper.jsx";
import QuestionPapers from './components/questionPapers/QuestionPapers.jsx';
import Homepage from "./components/homepage/Homepage.jsx";

function App() {
  const location = useLocation();
  const shownav = ['/', '/login', '/register'];
  const shouldShowNavbar = shownav.includes(location.pathname);

  return (
    <>
      {shouldShowNavbar && <Navbar />}

      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/:username/generatepaper" element={<GeneratePaper />} />
        <Route path="/:username/papers" element={<QuestionPapers />} />

        {/* Optional future routes */}
        <Route path="/QuestionPapers" element={<QuestionPapers />} />
        <Route path="/generatepaper" element={<GeneratePaper />} />
      </Routes>
    </>
  );
}

export default App;
