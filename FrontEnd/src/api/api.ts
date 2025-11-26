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

export function getRoleFromToken(tokenString?: string): string | null {
  // Se um token for passado como argumento, use-o. Caso contrário, tente buscar no sessionStorage.
  const token = tokenString || sessionStorage.getItem('token');
  
  if (!token) return null;

  try {
    const payloadBase64 = token.split('.')[1];
    if (!payloadBase64) return null;

    const payloadJson = atob(payloadBase64.replace(/-/g, "+").replace(/_/g, "/"));
    const payload = JSON.parse(payloadJson);

    if (payload.roles && Array.isArray(payload.roles) && payload.roles.length > 0) {
      return payload.roles[0];
    }
    
    return null;
  } catch (error) {
    console.error("Erro ao decodificar token:", error);
    return null;
  }
}

export default api;