import React, {useState , useRef ,useCallback} from "react";
import axios from "axios";

export function Join() {

    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await axios.post("/register", {
            username: username,
            password: password,

        });
        if (response.status === 200) {
            alert('회원 가입 성공!');
        } else {
            alert(response.status + "회원 가입 실패!");
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
                    <p></p>
                </div>
                <button type="submit">회원가입</button>
            </form>
        </div>
    );

}