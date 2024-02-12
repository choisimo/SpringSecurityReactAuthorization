import {useContext, useEffect, useState} from "react";
import axios from "axios";
import {HttpHeadersContext} from "../context/HttpHeaderProvider";

export function UserPage() {
    const userToken = localStorage.getItem("userToken");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("/user/1", {
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8",
                        "Authorization": userToken,
                    },
                });

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

        fetchData();
    }, []);
}