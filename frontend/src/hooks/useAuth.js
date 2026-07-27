import { useState } from 'react'

const STORAGE_KEY = 'wecode-user'

export function useAuth() {
  const [user, setUser] = useState(() => {
    if (typeof window === 'undefined') {
      return null
    }

    const savedUser = window.localStorage.getItem(STORAGE_KEY)
    return savedUser ? JSON.parse(savedUser) : null
  })

  const login = (credentials = {}) => {
    const nextUser = {
      id: Date.now(),
      name: credentials.name || 'Demo User',
      email: credentials.email || 'demo@wecode.app',
      role: credentials.role || 'student',
    }

    if (typeof window !== 'undefined') {
      window.localStorage.setItem(STORAGE_KEY, JSON.stringify(nextUser))
    }

    setUser(nextUser)
    return nextUser
  }

  const logout = () => {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(STORAGE_KEY)
    }

    setUser(null)
  }

  return {
    user,
    isAuthenticated: Boolean(user),
    login,
    logout,
  }
}
