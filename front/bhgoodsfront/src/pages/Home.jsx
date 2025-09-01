import React, { useEffect, useState, useRef } from 'react';
import { listarPublicos } from '../api/restauranteService';
import RestaurantFilters from '../components/RestaurantFilters';

export default function Home() {
  const [restaurantes, setRestaurantes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);
  const [filtros, setFiltros] = useState({});

  async function carregar(f = filtros) {
    try { setLoading(true); setErro(null); const res = await listarPublicos(f); setRestaurantes(res); }
    catch (e) { setErro(e.message); }
    finally { setLoading(false); }
  }

  useEffect(() => { carregar(); /* eslint-disable-next-line */ }, []);

  function onFiltrar(f) { setFiltros(f); carregar(f); }

  return (
    <div className="home-layout">
      <Header />
      <div className="home-body">
        <aside className="sidebar">
          <div className="sidebar-section">
            <h3>Filtros</h3>
            <RestaurantFilters onChange={onFiltrar} />
          </div>
        </aside>
        <main className="content">
          <section className="hero">
            <h1>Os melhores lugares para você pedir e aproveitar com a família e amigos!</h1>
            <SearchBar onSearch={nome => onFiltrar({ ...filtros, nome })} />
          </section>
          <section className="suggestions">
            <SectionTitle>Sugestões para você</SectionTitle>
            {loading && <p>Carregando...</p>}
            {erro && <p className="erro">{erro}</p>}
            <CardCarousel restaurantes={restaurantes} />
          </section>
        </main>
      </div>
    </div>
  );
}

function Header() {
  return (
    <header className="app-header">
      <div className="logo">bhgoods</div>
      <nav className="nav-actions">
        <button className="ghost">Buscar</button>
        <button className="ghost">Favoritos</button>
        <a href="/login" className="primary">Login</a>
        <button className="ghost">Menu</button>
      </nav>
    </header>
  );
}

function SearchBar({ onSearch }) {
  const [value, setValue] = useState('');
  return (
    <div className="search-bar">
      <input
        value={value}
        onChange={e => setValue(e.target.value)}
        placeholder="Buscar restaurantes ou pratos..."
      />
      <button onClick={() => onSearch(value)}>Buscar</button>
    </div>
  );
}

function SectionTitle({ children }) {
  return <h2 className="section-title">{children}</h2>;
}

function CardGrid({ restaurantes }) {
  if (!restaurantes.length) return <p>Nenhum restaurante encontrado.</p>;
  return (
    <div className="card-grid">
      {restaurantes.map(r => <RestaurantCard key={r.id || r.nome} r={r} />)}
    </div>
  );
}

function CardCarousel({ restaurantes }) {
  const scrollerRef = useRef(null);
  if (!restaurantes.length) return <p>Nenhum restaurante encontrado.</p>;

  function scroll(dir) {
    const el = scrollerRef.current;
    if (!el) return;
    const amount = 320; // scroll card width chunk
    el.scrollBy({ left: dir * amount, behavior: 'smooth' });
  }

  return (
    <div className="carousel-wrapper">
      <button className="carousel-arrow left" onClick={() => scroll(-1)} aria-label="Anterior">‹</button>
      <div className="carousel-scroller" ref={scrollerRef}>
        {restaurantes.map(r => <RestaurantCard key={r.id || r.nome} r={r} />)}
      </div>
      <button className="carousel-arrow right" onClick={() => scroll(1)} aria-label="Próximo">›</button>
    </div>
  );
}

function RestaurantCard({ r }) {
  // Fallbacks
  const foto = r.fotos?.[0];
  return (
    <a className="rest-card" href={r.id ? `/restaurantes/${r.id}` : '#'}>
      <div className="rest-card-img-wrapper">
        {foto ? <img src={foto} alt={r.nome} /> : <div className="img-placeholder">Sem foto</div>}
      </div>
      <div className="rest-card-body">
        <h3 className="rest-name">{r.nome}</h3>
        {r.descricao && <p className="rest-desc" title={r.descricao}>{r.descricao}</p>}
        <div className="rest-tags">
          {r.categorias?.map(c => <span key={c}>{c}</span>)}
        </div>
        {r.cardapio?.itens?.length ? (
          <div className="rest-highlight">
            {/* Exibe primeiro item como destaque */}
            <span className="item-nome">{r.cardapio.itens[0].nome}</span>
            <span className="item-preco">R$ {(r.cardapio.itens[0].preco/100).toFixed(2)}</span>
          </div>
        ) : <div className="rest-highlight vazio">Sem itens</div>}
      </div>
    </a>
  );
}

