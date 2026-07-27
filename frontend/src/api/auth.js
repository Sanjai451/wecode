export async function loginUser(credentials) {
  return Promise.resolve({
    user: {
      id: 1,
      name: credentials.name || 'Demo User',
      email: credentials.email || 'demo@wecode.app',
      role: 'student',
    },
    token: 'demo-token',
  })
}

export async function registerUser(credentials) {
  return Promise.resolve({
    user: {
      id: 2,
      name: credentials.name || 'New User',
      email: credentials.email || 'new@wecode.app',
      role: 'student',
    },
    token: 'demo-token',
  })
}
