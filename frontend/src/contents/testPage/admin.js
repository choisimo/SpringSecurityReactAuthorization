import {useEffect} from "react";
import axios from "axios";


export function AdminPage() {
    useEffect(() => {

        const userToken = localStorage.getItem("userToken");
        const headers = {
            Authorization: `Bearer ${userToken}`,
        };

        const fetchData = async () => {
            try {
                const response = await axios.get("/admin/1", {headers});
                if (response.status === 200) {
                    alert(response.data);
                }
            } catch (error) {
                console.error("admin page axios failed!:", error);
            }
        };

        fetchData();
    }, []);
}
