import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { loginUser } from '../api/auth'
import { useAuth } from '../hooks/useAuth'

function LoginPage() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [form, setForm] = useState({ email: '', password: '' })
  const [message, setMessage] = useState('')

  const handleSubmit = async (event) => {
    event.preventDefault()
    setMessage('')

    const result = await loginUser(form)
    login({
      name: result.user.name,
      email: result.user.email,
      role: result.user.role,
    })

    setMessage('Welcome back! Redirecting...')
    navigate('/problems')
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h2>Welcome to WeCode</h2>
        <p>Sign in to continue solving coding challenges.</p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label>
            Email
            <input
              type="email"
              value={form.email}
              onChange={(event) => setForm({ ...form, email: event.target.value })}
              required
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={form.password}
              onChange={(event) => setForm({ ...form, password: event.target.value })}
              required
            />
          </label>

          <button type="submit">Sign in</button>
        </form>

        {message ? <p className="form-message">{message}</p> : null}

        <p className="auth-switcher">
          New here? <Link to="/register">Create an account</Link>
        </p>
      </div>
    </div>
  )
}

export default LoginPage
