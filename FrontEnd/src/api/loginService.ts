import api from "./api";

export async function login(email: string, senha: string) {
  const { data } = await api.post("/access/login", { email, senha });

  sessionStorage.setItem("token", data.token);
  return data;
}
