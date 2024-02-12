import {useEffect} from "react";
import axios from "axios";


export function UserPage() {

    const userToken = localStorage.getItem("userToken");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("/user/1", {
                        headers: {
                            userToken,
                        },
                    });
                if (response.status === 200) {
                    alert(response.data);
                }
            } catch (error) {
                console.error("user page axios failed!:", error);
            }
        };
        fetchData();
    }, []);
}
