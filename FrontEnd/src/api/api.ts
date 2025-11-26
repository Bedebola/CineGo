import axios from "axios";

const API_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_URL,
  headers: { "Content-Type": "application/json" },
});

let storeInstance: any = null;

async function getStore() {
  if (!storeInstance) {
    const mod = await import("../Redux/store");
    storeInstance = mod.default || mod.store;
  }
  return storeInstance;
}

api.interceptors.request.use(
  async (config) => {
    const store = await getStore();
    const token = store.getState().auth.token;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (config.url !== "/access/login") {
      window.location.href = "/login";
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export async function getRoleFromToken(): Promise<string | null> {
  const store = await getStore();
  const token = store.getState().auth.token;
  if (!token) return null;

  try {
    const payloadBase64 = token.split(".")[1];
    const payloadJson = atob(payloadBase64);
    const payload = JSON.parse(payloadJson);

    return payload.roles?.[0] || null;
  } catch (error) {
    console.error("Erro ao decodificar token:", error);
    return null;
  }
}

export default api;
