import { api } from "./http";

export async function login(email: string, senha: string) {
  const res = await api("/access/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, senha }),
  });

  if (!res.ok) throw new Error("Falha no login");

  const data = await res.json();
  sessionStorage.setItem("token", data.token);
  return data;
}
