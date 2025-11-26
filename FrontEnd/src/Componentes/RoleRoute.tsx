import React from "react";
import { Navigate } from "react-router-dom";
import { getRoleFromToken } from "../api/api";

interface RoleRouteProps {
  children: React.ReactNode;
  allowedRoles: string[];
}

const RoleRoute: React.FC<RoleRouteProps> = ({ children, allowedRoles }) => {
  const [role, setRole] = React.useState<string | null>(null);

  React.useEffect(() => {
    async function fetchRole() {
      const userRole = await getRoleFromToken();
      setRole(userRole);
    }
    fetchRole();
  }, []);

  if (role === null) return null;

  if (!allowedRoles.includes(role)) {
    return <Navigate to="/login" />;
  }

  return <>{children}</>;
};

export default RoleRoute;
