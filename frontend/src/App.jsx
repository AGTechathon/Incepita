import Navbar from "./components/navbar/Navbar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Login from './components/login/Login.jsx';
import Register from "./components/register/Register.jsx";
import Sidebar from './components/sidebar/Sidebar.jsx';
import GeneratePaper from "./components/generatepaper/GeneratePaper.jsx";
// import SingleQuestion from "./components/singlequestion/SingleQuestion.jsx";

function App() {
  const location = useLocation();
  const shownav = ['/', '/login', '/register'];
  const shouldShowNavbar = shownav.includes(location.pathname);
  
  
  

  return(
     <>
      {/* <Navbar /> */}
      {/* <SingleQuestion>  </SingleQuestion> */}
      {shouldShowNavbar && <Navbar/> }
     
     
      <Routes>
        { /* <Route path="/generatebutton" element={<GenerateBtn />} />
         <Route path="/generate" element={<Generate />} /> */}
        <Route path="/:username/generatepaper" element={<GeneratePaper />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/:username/generatepaper" element={<GeneratePaper />} />
        <Route path="/:username/papers" element={<QuestionPapers />} />
        <Route path="/:username/papers/:questionPaperId/:targetVersion"  element={ <SingleQuestion /> }/>
        {/* Optional future routes */}
        <Route path="/QuestionPapers" element={<QuestionPapers />} />
        <Route path="/generatepaper" element={<GeneratePaper />} />
      </Routes>
    </>
  );
}

export default App;
