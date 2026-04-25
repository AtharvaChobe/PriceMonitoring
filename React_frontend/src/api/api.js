import axios from "axios";

const API = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL,
});

// Attach JWT automatically
API.interceptors.request.use((req) => {
  const token = localStorage.getItem("token");
  if (token) req.headers.Authorization = `Bearer ${token}`;
  return req;
});

export const register = (data) => API.post("/user/register", data);
export const login = (data) => API.post("/user/login", data);

export const createAlert = (data) => API.post("/alert", data);
export const getAlerts = (userId) => API.get(`/alert/user/${userId}`);
export const deleteAlert = (id) => API.delete(`/alert/${id}`);

export const getCurrentPrice = (metal, city) => 
 API.get(`/data?metal=${metal}&city=${city}`);