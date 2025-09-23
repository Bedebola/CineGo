const API_URL = "http://localhost:8080";

export const api = (endpoint: string, options?: RequestInit) => {
  return fetch(`${API_URL}${endpoint}`, options);
};
