import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
    // baseURL: "http://192.168.0.124:8080/api", // Spring Boot
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;
        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            try {
                const refreshToken = localStorage.getItem("refreshToken");
                console.log("Stored refreshToken:", refreshToken);
                if (!refreshToken) {
                    throw new Error("No refresh token found");
                }
                const response = await axios.post("http://localhost:8080/api/users/refresh", null, {
                    headers: { Authorization: `Bearer ${refreshToken}` },
                });
                const newAccessToken = response.data.accessToken;
                localStorage.setItem("token", newAccessToken);
                originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                return api(originalRequest);
            } catch (err) {
                console.error("Refresh token expired or invalid", err);
                localStorage.removeItem("token");
                localStorage.removeItem("refreshToken");
                window.location.href = "/login"; // 로그인 페이지로 리디렉션
                return Promise.reject(err);
            }
        }
        return Promise.reject(error);
    }
);

export default api;
