import axios from 'axios'

// Base URL of your Spring Boot backend
const API_BASE_URL = 'http://localhost:8080/api'

// Register a new user
export const registerUser = async (userData) => {
  return await axios.post(`${API_BASE_URL}/auth/register`, userData)
}

// Login and get JWT token
export const loginUser = async (credentials) => {
  return await axios.post(`${API_BASE_URL}/auth/login`, credentials)
}

// Call USER dashboard API (sends token in header)
export const getUserDashboard = async () => {
  const token = localStorage.getItem('token')
  return await axios.get(`${API_BASE_URL}/dashboard/user`, {
    headers: {
      Authorization: `Bearer ${token}`   // JWT sent here
    }
  })
}

// Call ADMIN dashboard API (sends token in header)
export const getAdminDashboard = async () => {
  const token = localStorage.getItem('token')
  return await axios.get(`${API_BASE_URL}/dashboard/admin`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
}