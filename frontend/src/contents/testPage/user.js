import { useContext, useEffect } from "react";
import axios from "axios";
import { HttpHeadersContext } from "../context/HttpHeaderProvider";

export function UserPage() {
    const userToken = localStorage.getItem("userToken");

    useEffect(() => {
        console.log("useEffect /user/1 running!");
        const fetchData = async () => {
            try {
                const response = await axios.post(
                    "/user/1",
                    {
                        headers: {
                            "Content-Type": "application/json;charset=UTF-8",
                            "Authorization": userToken,
                        },
                    }
                );

                if (response && response.status === 200) {
                    console.log("/user/1 get data: ", response.data);
                    alert(response.data);
                } else {
                    console.error("/user/1 request failed with status:", response ? response.status : "Unknown");
                }
            } catch (error) {
                console.error("user page axios failed!:", error);
            }
        };
        console.log("UserPage rendered!");

        fetchData();
    }, []);
}