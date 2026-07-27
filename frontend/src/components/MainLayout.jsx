import { Link, NavLink, Outlet } from 'react-router-dom'
import { useAuth } from '../../hooks/useAuth'

const navItems = [
  { to: '/problems', label: 'Problems' },
  { to: '/editor', label: 'Editor' },
  { to: '/submissions', label: 'Submissions' },
  { to: '/profile', label: 'Profile' },
]

function MainLayout() {
  const { user, logout } = useAuth()

  return (
    <div className="app-shell">
      <header className="topbar">
        <Link className="brand" to="/problems">
          <span className="brand-mark">W</span>
          <span>WeCode</span>
        </Link>

        <nav className="nav-links" aria-label="Primary navigation">
          {navItems.map((item) => (
            <NavLink key={item.to} to={item.to} className="nav-link">
              {item.label}
            </NavLink>
          ))}
        </nav>

        <div className="user-actions">
          {user ? (
            <>
              <span className="user-pill">Hi, {user.name}</span>
              <button className="ghost-button" type="button" onClick={logout}>
                Logout
              </button>
            </>
          ) : (
            <Link className="ghost-button" to="/login">
              Sign in
            </Link>
          )}
        </div>
      </header>

      <main className="content">
        <Outlet />
      </main>
    </div>
  )
}

export default MainLayout
