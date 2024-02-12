import React, {useRef, useState} from "react";
import axios from "axios";


export function Login(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const info = useRef({password: ""})
    const handleInputChange = (e, key) => {
        const value = e.target.value;
        info.current[key] = value;
        if (key === "password"){
            setPassword(value);
        } else {
            setUsername(value);
        }
    }

    const setUserToken = (token) => {
        localStorage.setItem('userToken', token);
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await axios.post("/login", {
            username: username,
            password: password,
        });
        if (response.status === 200) {
            const token = response.headers;
            if(token){
                console.log(token);
                setUserToken(response.headers);
                alert("토큰 받음!");
            } else {
                console.log("토큰 없음!");
            }
        } else {
            alert(response.status + "토큰 생성 실패");
        }
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div className="joinForm">
                    <p>이름</p>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => handleInputChange(e, "username")}
                    />
                    <p>비밀번호</p>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => handleInputChange(e, "password")}
                    />
                </div>
                <p></p>
                <button type="submit">로그인</button>
            </form>
        </div>
    );
}