import logo from './logo.svg';
import './App.css';
import {Route, Routes} from "react-router-dom";
import {Join} from "contents/member/join";
import {Login} from "contents/member/login";
function App() {
  return (
      <div>
        <Routes>
          <Route exact path="/join" element={<Join/>}/>
          <Route exact path="/login" element={<Login/>}/>
        </Routes>
      </div>
  )
}

export default App;
