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
                            "Content-Type": `application/json;charset=UTF-8`,
                            "Authorization": encodeURIComponent("Bearer "+userToken),
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
