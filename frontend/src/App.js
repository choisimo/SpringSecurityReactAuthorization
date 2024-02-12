import logo from './logo.svg';
import './App.css';
import {Route, Routes} from "react-router-dom";
import {Join} from "contents/member/join";
import {Login} from "contents/member/login";
import {AdminPage} from "contents/testPage/admin";
import {UserPage} from "contents/testPage/user";
function App() {
  return (
      <div>
        <Routes>
          <Route exact path="/join" element={<Join/>}/>
          <Route exact path="/login" element={<Login/>}/>
          <Route exact path="/admin/1" element={<AdminPage/>}/>
          <Route exact path="/user/1" element={<UserPage/>}/>
        </Routes>
      </div>
  )
}

export default App;
