import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getAdminDashboard } from '../services/api'

function AdminDashboard() {
  const navigate = useNavigate()
  const [message, setMessage] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getAdminDashboard()
      .then(res => {
        setMessage(res.data)
        setLoading(false)
      })
      .catch(() => {
        alert('Access denied or session expired.')
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
    <div className="min-h-screen bg-gradient-to-br from-red-50 to-rose-100">

      {/* Navbar */}
      <nav className="bg-white shadow-sm px-6 py-4 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <span className="text-2xl">🛡️</span>
          <span className="font-bold text-gray-800 text-lg">Admin Dashboard</span>
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
              <div className="bg-red-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                <span className="text-4xl">👑</span>
              </div>
              <h2 className="text-2xl font-bold text-gray-800 mb-2">
                Admin Panel
              </h2>
              <p className="text-red-600 font-medium text-lg mb-4">{message}</p>

              {/* Role Badge */}
              <span className="inline-block bg-red-100 text-red-700 text-sm font-semibold px-4 py-1 rounded-full">
                Role: ADMIN
              </span>

              <div className="mt-8 bg-gray-50 rounded-xl p-4 text-left">
                <p className="text-sm text-gray-500 font-medium mb-2">✅ Admin Access Granted</p>
                <p className="text-sm text-gray-500">✅ JWT Token Verified</p>
                <p className="text-sm text-gray-500">✅ Role: ADMIN confirmed</p>
                <p className="text-sm text-gray-500">✅ Full system access</p>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default AdminDashboard