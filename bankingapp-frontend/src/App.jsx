import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import './css/Home.css'


function App() {
  return (
    <Router>
      
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route
          path="*"
          element={
            <div className="home-container">
              <div className="home-card">
                <h1>Welcome to MyBank</h1>
                <p>Secure. Reliable. Fast.</p>
                <div className="home-buttons">
                  <Link to="/login" className="home-btn">Login</Link>
                  <Link to="/register" className="home-btn">Register</Link>
                </div>
              </div>
            </div>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
