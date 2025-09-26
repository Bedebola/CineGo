import { http } from "./http";

export async function login(email: string, senha: string) {
  const { data } = await http.post("/access/login", { email, senha });

  sessionStorage.setItem("token", data.token);
  return data;
}
