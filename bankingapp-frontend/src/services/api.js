import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // change if different
});

export default api;
