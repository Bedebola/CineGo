export default function Footer() {
  return (
    <footer
      className="position-fixed bottom-0 start-0 end-0 bg-black border-top border-secondary text-white shadow-sm"
      style={{ marginLeft: 250, zIndex: 1040 }}
    >
      <div className="container py-3 text-center small">
        <p className="m-0">&copy; {new Date().getFullYear()} CineGo by Be_debola. Todos os direitos reservados.</p>
      </div>
    </footer>
  );
}
