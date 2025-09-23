import { Outlet } from "react-router-dom";
import Header from "../Header";
import Footer from "../Footer";
import Sidebar from "../Sidebar/sidebar";

function Layout() {
  return (
    <>
      <Header />
      <Sidebar />

      <main
        className="container-fluid"
        style={{
          marginLeft: 250,
          paddingTop: 72, 
          paddingBottom: 64,
          minHeight: "100vh",
        }}
      >
        <div className="p-4">
          <Outlet />
        </div>
      </main>

      <Footer />
    </>
  );
}

export default Layout;
