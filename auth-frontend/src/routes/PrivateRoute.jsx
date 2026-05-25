import { Navigate } from 'react-router-dom'

// allowedRole = "USER" or "ADMIN"
// children = the page component to show if authorized
function PrivateRoute({ children, allowedRole }) {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  // If no token, user is not logged in → send to login
  if (!token) {
    return <Navigate to="/login" />
  }

  // If role doesn't match → send to login
  if (allowedRole && role !== allowedRole) {
    return <Navigate to="/login" />
  }

  // All checks passed → show the protected page
  return children
}

export default PrivateRoute