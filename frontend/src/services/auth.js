export function saveAuth(data) {
  localStorage.setItem('token', data.token)
  localStorage.setItem('userRole', data.role)
  localStorage.setItem('username', data.username)
}

export function clearAuth() {
  localStorage.removeItem('token')
  localStorage.removeItem('userRole')
  localStorage.removeItem('username')
}

export function getToken() {
  return localStorage.getItem('token')
}

export function getRole() {
  return localStorage.getItem('userRole')
}

export function getUsername() {
  return localStorage.getItem('username')
}

export function isAuthenticated() {
  return !!getToken()
}

export function isAdmin() {
  return getRole() === 'ADMIN'
}
