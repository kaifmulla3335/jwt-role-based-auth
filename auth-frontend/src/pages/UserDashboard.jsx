import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getUserDashboard } from '../services/api'

function UserDashboard() {
  const navigate = useNavigate()
  const [message, setMessage] = useState('')
  const [loading, setLoading] = useState(true)

  // Get name from localStorage (optional — if you save it during login)
  const userEmail = localStorage.getItem('email') || 'User'

  useEffect(() => {
    getUserDashboard()
      .then(res => {
        setMessage(res.data)
        setLoading(false)
      })
      .catch(() => {
        alert('Session expired. Please login again.')
        navigate('/login')
      })
  }, [])

  const handleLogout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('email')
    navigate('/login')
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100">

      {/* Navbar */}
      <nav className="bg-white shadow-sm px-6 py-4 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <span className="text-2xl">👤</span>
          <span className="font-bold text-gray-800 text-lg">User Dashboard</span>
        </div>
        <button
          onClick={handleLogout}
          className="bg-red-500 hover:bg-red-600 text-white text-sm px-4 py-2 rounded-lg transition"
        >
          Logout
        </button>
      </nav>

      {/* Content */}
      <div className="flex items-center justify-center mt-20 px-4">
        <div className="bg-white rounded-2xl shadow-xl p-10 w-full max-w-lg text-center">

          {loading ? (
            <div className="text-gray-400 text-lg">Loading...</div>
          ) : (
            <>
              <div className="bg-green-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                <span className="text-4xl">🎉</span>
              </div>
              <h2 className="text-2xl font-bold text-gray-800 mb-2">
                Welcome!
              </h2>
              <p className="text-green-600 font-medium text-lg mb-4">{message}</p>

              {/* Role Badge */}
              <span className="inline-block bg-green-100 text-green-700 text-sm font-semibold px-4 py-1 rounded-full">
                Role: USER
              </span>

              <div className="mt-8 bg-gray-50 rounded-xl p-4 text-left">
                <p className="text-sm text-gray-500 font-medium mb-2">✅ Your JWT Token is Valid</p>
                <p className="text-sm text-gray-500">✅ You are authenticated</p>
                <p className="text-sm text-gray-500">✅ Role-based access working</p>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default UserDashboard